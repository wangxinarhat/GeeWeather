package wang.wangxinarhat.geeweather.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by wang on 2016/3/4.
 */
public class ResourcesUtils {

    public static final String TYPE_DRAWABLE = "drawable";
    public static final String TYPE_RAW = "raw";

    /**
     * use resouce name to get resource id
     *
     * @param context
     * @param resourceName the resouce name that you want to get it's id
     * @param type         resource type,contains:
     * @return -1 illegal input params, !=-1 resouce id
     */
    public static int getResourceId(Context context, String resourcesName, String type) {
        if (null == context || TextUtils.isEmpty(resourcesName) || TextUtils.isEmpty(type)) {
            return -1;
        }

        return context.getResources().getIdentifier(resourcesName, type, context.getPackageName());
    }

}
