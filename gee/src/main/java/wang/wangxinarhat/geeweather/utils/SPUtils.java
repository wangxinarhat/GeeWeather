package wang.wangxinarhat.geeweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import wang.wangxinarhat.geeweather.global.BaseApplication;
import wang.wangxinarhat.geeweather.global.Constants;

/**
 * SharePreferences工具类
 * Created by sacowiw on 16/1/22.
 */
public class SPUtils {
    /**
     * 获得位置信息信息sharePreferences
     *
     * @return
     */
    public static SharedPreferences getCityInfoSP() {
        return BaseApplication.getApplication().getSharedPreferences(Constants.CITY_INFO, Context.MODE_PRIVATE);
    }


    public static void setCityInfo(String city, String district) {

        getCityInfoSP().edit().putString("city", city)
                .putString("district", district).commit();
    }

    public static String getCity() {

        return getCityInfoSP().getString("city", "上海");
    }

    public static String getDistrict() {

        return getCityInfoSP().getString("district", "上海");
    }
}
