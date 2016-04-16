package wang.wangxinarhat.geeweather.global;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import wang.wangxinarhat.geeweather.db.LocalConfig;
import wang.wangxinarhat.geeweather.db.WeatherDbHelper;

/**
 * Created by wang on 2016/3/4.
 */
public class BaseApplication extends Application implements AMapLocationListener {

    private static String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication mApplication;

    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }


    public static String cacheDir = "";
    public static Context mAppContext = null;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new GeeAMapLocationListener();

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;


    //cityID
    private WeatherDbHelper mWeatherDbHelper = null;
    private final int DB_EXISTS = 0;
    private final int DB_COPY_COMPLETE = 1;
    private final int DB_COPY_START = 2;


    @Override
    public void onCreate() {
        super.onCreate();

        if (null == mApplication) {
            mApplication = this;
        }
        mAppContext = getApplicationContext();
        // 初始化 retrofit
//        RetrofitSingleton.init(getApplicationContext());
//        CrashHandler.init(new CrashHandler(getApplicationContext()));

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }


        copyCityId2DB();
        initLocation();

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


    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将asset下的city复制到数据库中
     */
    private void copyCityId2DB() {
        File dbFile = getDatabasePath(WeatherDbHelper.DB_WEATHER_CITY_ID_NAME);
        if (dbFile.exists()) {

            Log.e(TAG, "数据库已经存在了，无需复制");
            return;
        }
        //复制文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                copy();
            }
        }).start();
    }

    private void copy() {
        BufferedReader mBufferedReader = null;
        WeatherDbHelper dbHelper = new WeatherDbHelper(this, WeatherDbHelper.DB_WEATHER_CITY_ID_NAME, null, LocalConfig.DB_WEATHER_VERSION);
        SQLiteDatabase mSqLiteDatabase = dbHelper.getWritableDatabase();
        String table_name = WeatherDbHelper.DB_WEATHER_CITY_ID_TABLE_NAME;
        ContentValues mValues = new ContentValues();

        try {
            mBufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(WeatherDbHelper.DB_WEATHER_CITY_ID_NAME), "UTF-8"));
            while (true) {
                String strLine = mBufferedReader.readLine();
                if (TextUtils.isEmpty(strLine)) {
                    break;
                }
                String[] cityInfos = strLine.trim().split(",");
                if (cityInfos != null && cityInfos.length > 0) {
                    String id = cityInfos[0];
                    String spell_zh = cityInfos[1];
                    String area = cityInfos[2];
                    String town = cityInfos[3];
                    String province = cityInfos[4];//省
                    mValues.put(WeatherDbHelper.DB_WEATHER_CITY_ID, id);
                    mValues.put(WeatherDbHelper.DB_WEATHER_CITY_SPELL_ZH, spell_zh);
                    mValues.put(WeatherDbHelper.DB_WEATHER_CITY_CITY_AREA, area);
                    mValues.put(WeatherDbHelper.DB_WEATHER_CITY_CITY_TOWN, town);
                    mValues.put(WeatherDbHelper.DB_WEATHER_CITY_CITY_PROVINCE, province);
                    mSqLiteDatabase.insert(table_name, null, mValues);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mBufferedReader.close();
                mSqLiteDatabase.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码

                aMapLocation.getAdCode();//地区编码
//                amapLocation.getAOIName();//获取当前定位点的AOI信息


                Log.e(TAG,
                        "  getLocationType  : " + aMapLocation.getLocationType() +
                                "  getAccuracy  : " + aMapLocation.getAccuracy() +
                                "  getCountry  : " + aMapLocation.getCountry() +
                                "  getProvince  : " + aMapLocation.getProvince() +
                                "  getCity  : " + aMapLocation.getCity() +
                                "  getDistrict  : " + aMapLocation.getDistrict() +
                                "  getStreet  : " + aMapLocation.getStreet() +
                                "  getStreet  : " + aMapLocation.getStreetNum() +
                                "  getCityCode  : " + aMapLocation.getCityCode() +
                                "  getCityCode  : " + aMapLocation.getAdCode() +
                                "  getAddress  : " + aMapLocation.getAddress()

                );
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
