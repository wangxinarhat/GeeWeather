package wang.wangxinarhat.geeweather.global;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.greenrobot.eventbus.EventBus;

import de.greenrobot.dao.query.QueryBuilder;
import wang.wangxinarhat.dao.CITY;
import wang.wangxinarhat.dao.CITYDao;
import wang.wangxinarhat.dao.DaoMaster;
import wang.wangxinarhat.dao.DaoSession;
import wang.wangxinarhat.geeweather.Event.MessageEvent;
import wang.wangxinarhat.geeweather.utils.SPUtils;
import wang.wangxinarhat.geeweather.utils.StringUtils;

/**
 * Created by wang on 2016/3/4.
 */
public class BaseApplication extends Application implements AMapLocationListener {

    private static String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication mApplication;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }


    public static Context mAppContext = null;
    public static String cacheDir = "";

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    public void onCreate() {
        super.onCreate();

        if (null == mApplication) {
            mApplication = this;
        }
        mAppContext = getApplicationContext();

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }


        initLocation();
        setupDatabase();

    }

    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(this);


//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms  30min
        mLocationOption.setInterval(1000 * 6);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                if (StringUtils.hasMeaningful(aMapLocation.getDistrict())) {
                    SPUtils.setCityInfo(aMapLocation.getCity(), aMapLocation.getDistrict());

                    if (!StringUtils.hasMeaningful(SPUtils.getDistrict())) {
                        search(aMapLocation.getProvince(), aMapLocation.getCity(), aMapLocation.getDistrict());
                        EventBus.getDefault().post(new MessageEvent());
                    }
                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    private CITY search(String city_province, String city_town, String city_area) {

        if (city_province != null) {
            city_province = city_province.replace("市", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("区", "")
                    .replace("盟", "");
        }
        if (city_town != null) {
            city_town = city_town.replace("市", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("区", "")
                    .replace("盟", "");
        }
        if (city_area != null) {
            city_area = city_area.replace("市", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("区", "")
                    .replace("盟", "");
        }
        //在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        return getCityInfoDao().queryBuilder().where(CITYDao.Properties.City_town.eq(city_town)).unique();
    }

    private CITYDao getCityInfoDao() {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        return daoSession.getCITYDao();
    }

}
