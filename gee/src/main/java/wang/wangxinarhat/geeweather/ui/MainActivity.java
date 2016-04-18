package wang.wangxinarhat.geeweather.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.race604.flyrefresh.FlyRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wang.wangxinarhat.geeweather.Event.MessageEvent;
import wang.wangxinarhat.geeweather.R;
import wang.wangxinarhat.geeweather.model.Setting;
import wang.wangxinarhat.geeweather.model.Weather;
import wang.wangxinarhat.geeweather.net.NetWork;
import wang.wangxinarhat.geeweather.net.operators.WeatherInfo2Weather;
import wang.wangxinarhat.geeweather.ui.adapter.WeatherAdapter;
import wang.wangxinarhat.geeweather.utils.LogUtils;
import wang.wangxinarhat.geeweather.utils.MyToast;
import wang.wangxinarhat.geeweather.utils.SPUtils;
import wang.wangxinarhat.geeweather.utils.SomeUtils;
import wang.wangxinarhat.geeweather.utils.StringUtils;

public class MainActivity extends BaseActivity implements FlyRefreshLayout.OnPullRefreshListener, NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.fly_layout)
    FlyRefreshLayout mFlyLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    private WeatherAdapter mAdapter;
    private Observer<Weather> observer;
    private boolean isLoading;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initIcon();

        initView();

        loadData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (null != mAdapter && null != mRecyclerview) {
            loadData();
        }
    }


    private void loadData() {

        String district = SPUtils.getDistrict();
        if (!StringUtils.hasMeaningful(district)) {
            district += "上海";
        }

        if (StringUtils.hasMeaningful(district)) {
            district = district.replace("市", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("区", "")
                    .replace("县", "")
                    .replace("盟", "");

            isLoading = true;
            subscription = NetWork.getWeatherApi()
                    .queryWeather(district, Setting.KEY)
                    .map(WeatherInfo2Weather.newInstance())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver());

        } else {
            MyToast.showShortToast("定位失败，请刷新重试");
        }


    }

    private Observer<? super Weather> getObserver() {

        if (null == observer) {
            observer = new Observer<Weather>() {
                @Override
                public void onCompleted() {

                    mFlyLayout.onRefreshFinish();

                    if (SomeUtils.isNetworkConnected(MainActivity.this)) {

                        Snackbar.make(mRecyclerview, "加载完毕，(*^▽^*)", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mRecyclerview, "网络异常，(ಥ _ ಥ)", Snackbar.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                }

                @Override
                public void onError(Throwable e) {
                    mFlyLayout.onRefreshFinish();

                    Snackbar.make(mRecyclerview, "网络异常，(ಥ _ ಥ)", Snackbar.LENGTH_SHORT).show();
                    LogUtils.LOGE(TAG, e.getMessage());

                    isLoading = false;
                }

                @Override
                public void onNext(Weather weather) {
                    if (null == mAdapter) {
                        mAdapter = new WeatherAdapter();
                    }
                    mRecyclerview.setAdapter(mAdapter);
                    mAdapter.setData(weather);
                }
            };
        }
        return observer;
    }


    private void initView() {


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);


        mFlyLayout.setOnPullRefreshListener(this);


        View actionButton = mFlyLayout.getHeaderActionButton();
        if (actionButton != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlyLayout.startRefresh();
                }
            });
        }


    }


    @Override
    public void onBackPressed() {

//        assert drawer != null;
        if (null != mDrawerLayout && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(mRecyclerview, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }


            super.onBackPressed();
        }
    }

    /**
     * 初始化Icon
     */
    private void initIcon() {
        if (mSetting.getInt(Setting.CHANGE_ICONS, 0) == 0) {
            mSetting.putInt("未知", R.mipmap.none);
            mSetting.putInt("晴", R.mipmap.type_one_sunny);
            mSetting.putInt("阴", R.mipmap.type_one_cloudy);
            mSetting.putInt("多云", R.mipmap.type_one_cloudy);
            mSetting.putInt("少云", R.mipmap.type_one_cloudy);
            mSetting.putInt("晴间多云", R.mipmap.type_one_cloudytosunny);
            mSetting.putInt("小雨", R.mipmap.type_one_light_rain);
            mSetting.putInt("中雨", R.mipmap.type_one_middle_rain);
            mSetting.putInt("大雨", R.mipmap.type_one_heavy_rain);
            mSetting.putInt("阵雨", R.mipmap.type_one_thunderstorm);
            mSetting.putInt("雷阵雨", R.mipmap.type_one_thunderstorm);
            mSetting.putInt("霾", R.mipmap.type_one_fog);
            mSetting.putInt("雾", R.mipmap.type_one_fog);
        } else {
            mSetting.putInt("未知", R.mipmap.none);
            mSetting.putInt("晴", R.mipmap.type_two_sunny);
            mSetting.putInt("阴", R.mipmap.type_two_cloudy);
            mSetting.putInt("多云", R.mipmap.type_two_cloudy);
            mSetting.putInt("少云", R.mipmap.type_two_cloudy);
            mSetting.putInt("晴间多云", R.mipmap.type_two_cloudytosunny);
            mSetting.putInt("小雨", R.mipmap.type_two_light_rain);
            mSetting.putInt("中雨", R.mipmap.type_two_rain);
            mSetting.putInt("大雨", R.mipmap.type_two_rain);
            mSetting.putInt("阵雨", R.mipmap.type_two_rain);
            mSetting.putInt("雷阵雨", R.mipmap.type_two_thunderstorm);
            mSetting.putInt("霾", R.mipmap.type_two_haze);
            mSetting.putInt("雾", R.mipmap.type_two_fog);
            mSetting.putInt("雨夹雪", R.mipmap.type_two_snowrain);
        }
    }


    @Override
    public void onRefresh(FlyRefreshLayout view) {

        if (!isLoading) {
            View child = mRecyclerview.getChildAt(0);
            if (child != null) {
                bounceAnimateView(child.findViewById(R.id.icon));
            }

            loadData();

        } else {
            Snackbar.make(mRecyclerview, "加载中，↖(^ω^)↗", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void bounceAnimateView(View view) {
        if (view == null) {
            return;
        }

        Animator swing = ObjectAnimator.ofFloat(view, "rotationX", 0, 30, -20, 0);
        swing.setDuration(400);
        swing.setInterpolator(new AccelerateInterpolator());
        swing.start();
    }

    @Override
    public void onRefreshAnimationEnd(FlyRefreshLayout view) {

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_geography) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
