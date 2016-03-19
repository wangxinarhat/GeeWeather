package wang.wangxinarhat.geeweather.global;

import android.app.Application;

/**
 * Created by wang on 2016/3/4.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    public static synchronized BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (null == mApplication) {
            mApplication = this;
        }
    }
}
