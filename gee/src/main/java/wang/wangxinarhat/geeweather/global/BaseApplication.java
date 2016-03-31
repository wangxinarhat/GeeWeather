package wang.wangxinarhat.geeweather.global;

import android.app.Application;
import android.content.Context;

import wang.wangxinarhat.geeweather.common.CrashHandler;
import wang.wangxinarhat.geeweather.common.RetrofitSingleton;

/**
 * Created by wang on 2016/3/4.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }





    public static String cacheDir = "";
    public static Context mAppContext = null;


    @Override public void onCreate() {
        super.onCreate();

        if (null == mApplication) {
            mApplication = this;
        }
        mAppContext = getApplicationContext();
        // 初始化 retrofit
        RetrofitSingleton.init(getApplicationContext());
        CrashHandler.init(new CrashHandler(getApplicationContext()));

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        }
        else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }


    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }
}
