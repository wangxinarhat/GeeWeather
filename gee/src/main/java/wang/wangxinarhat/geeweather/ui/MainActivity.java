package wang.wangxinarhat.geeweather.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.race604.flyrefresh.FlyRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wang.wangxinarhat.geeweather.R;
import wang.wangxinarhat.geeweather.model.Setting;
import wang.wangxinarhat.geeweather.model.Weather;
import wang.wangxinarhat.geeweather.net.NetWork;
import wang.wangxinarhat.geeweather.net.operators.WeatherInfo2Weather;
import wang.wangxinarhat.geeweather.ui.adapter.WeatherAdapter;
import wang.wangxinarhat.geeweather.utils.LogUtils;
import wang.wangxinarhat.geeweather.utils.MyToast;

public class MainActivity extends BaseActivity implements FlyRefreshLayout.OnPullRefreshListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.fly_layout)
    FlyRefreshLayout flyLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private WeatherAdapter adapter;
    private Observer<Weather> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initIcon();

        initView();

        loadData();

    }

    private void loadData() {


//        unsubscribe();
        subscription = NetWork.getWeatherApi()
                .queryWeather("CN101010100", Setting.KEY)
                .map(WeatherInfo2Weather.newInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());


    }

    private Observer<? super Weather> getObserver() {

        if (null == observer) {
            observer = new Observer<Weather>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    flyLayout.onRefreshFinish();

                    LogUtils.LOGE(TAG, e.getMessage());

                    MyToast.showConnectError();
                }

                @Override
                public void onNext(Weather weather) {
                    flyLayout.onRefreshFinish();
                    if (null == adapter) {
                        adapter = new WeatherAdapter();
                    }
                    recyclerview.setAdapter(adapter);
                    adapter.setData(weather);
                }
            };
        }
        return observer;
    }


    private void initView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("are you wangwangstar? ^_^");


        flyLayout.setOnPullRefreshListener(this);



        View actionButton = flyLayout.getHeaderActionButton();
        if (actionButton != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flyLayout.startRefresh();
                }
            });
        }

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                -1 up; 1 down

                if (!recyclerView.canScrollVertically(-1)) {
//                    LogUtils.LOGE(TAG, "-1-1-1-1-1-1");
//                    onScrolledToTop();

//                    (!recyclerView.canScrollVertically(1))
                } else  {
//                    flyLayout.setNestedScrollingEnabled(false);
//                    LogUtils.LOGE(TAG, "1111111111111111111111");
//                    onScrolledToBottom();
                }
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        assert drawer != null;
        if (null != drawer && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        View child = recyclerview.getChildAt(0);
        if (child != null) {
            bounceAnimateView(child.findViewById(R.id.icon));
        }

        loadData();


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


}
