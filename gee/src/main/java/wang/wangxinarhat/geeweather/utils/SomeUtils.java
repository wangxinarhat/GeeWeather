package wang.wangxinarhat.geeweather.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import wang.wangxinarhat.geeweather.global.BaseApplication;


/**
 * 一些乱七八糟的工具集合
 * Created by sacowiw on 15/10/24.
 */
public class SomeUtils {
    /**
     * 清空常规（动态,训练,资讯）内容的提示数量
     *
     * @param type
     * @param cid
     */
//    public static void clearNormal(String type, String cid) {
//        String url = Uri.parse(AppContent.CLEAR_NORMAL).buildUpon().appendQueryParameter("uid", BaseApplication.getApplication().getUid()).
//                appendQueryParameter("token", BaseApplication.getApplication().getToken()).
//                appendQueryParameter("type", type).
//                appendQueryParameter("cid", cid).build().toString();
//        VolleyHelper.get(url, new VolleyHelper.Callback() {
//            @Override
//            public void success(String response) {
//
//            }
//
//            @Override
//            public void error(VolleyError error) {
//
//            }
//        });
//    }

    /**
     * 清空单个系统内容消息的提示数量
     *
     * @param cid
     */
//    public static void clearSystem(String cid) {
//        String url = Uri.parse(AppContent.CLEAR_SYSTEM).buildUpon().
//                appendQueryParameter("uid", BaseApplication.getApplication().getUid()).
//                appendQueryParameter("token", BaseApplication.getApplication().getToken()).
//                appendQueryParameter("cid", cid).build().toString();
//        VolleyHelper.get(url, new VolleyHelper.Callback() {
//            @Override
//            public void success(String response) {
//
//            }
//
//            @Override
//            public void error(VolleyError error) {
//
//            }
//        });
//    }

    /**
     * 修改tablayout字体
     *
     * @param tabLayout
     */
//    public static void changeTabsFont(final TabLayout tabLayout, ViewPager viewPager) {
//        changeTagsFontEveryWhere(tabLayout, 0);
//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                super.onTabSelected(tab);
//                changeTagsFontEveryWhere(tabLayout, tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                super.onTabUnselected(tab);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                super.onTabReselected(tab);
//            }
//        });
//
//    }

//    private static void changeTagsFontEveryWhere(TabLayout tabLayout, int position) {
//        if (tabLayout != null) {
//            try {
//                Typeface tp = Typeface.createFromFile(AppContent.FONT_NORMAL_PATH);
//                Typeface tpBold = Typeface.createFromFile(AppContent.FONT_BOLD_PATH);
//
//                ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//                int tabsCount = vg.getChildCount();
//                for (int j = 0; j < tabsCount; j++) {
//                    ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//                    int tabChildsCount = vgTab.getChildCount();
//                    for (int i = 0; i < tabChildsCount; i++) {
//                        View tabViewChild = vgTab.getChildAt(i);
//                        if (tabViewChild instanceof TextView) {
//                            if (j == position) {
//                                ((TextView) tabViewChild).setTypeface(tpBold);
//                            } else {
//                                ((TextView) tabViewChild).setTypeface(tp);
//                            }
//
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                Log.e("wangqiujia", "Font is not exist");
//            }
//        }
//    }

    /**
     * 数字大于10000的将格式化为*。*万
     *
     * @param s
     * @return
     */
    public static String formatNumber(String s) {

        if (s != null) {
            if (s.length() < 5) {
                return s;
            } else {
                return String.format("%.1f", Double.valueOf(s) / 10000) + "万";
            }
        } else {
            return "0";
        }
    }

    /**
     * 向桌面创建快捷方式
     *
     * @param context
     */
//    public static void createShortcut(Context context) {
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//        Intent createIntent = new Intent();
//        createIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//        createIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
//        createIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher));
//        createIntent.putExtra("duplicate", false);
//        createIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//        context.sendBroadcast(createIntent);
//    }

    /**
     * 点击区域外收起键盘
     */
    public static void autoHideInput(final View view, final InputMethodManager imm) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return false;
                }

            });
        }

        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                autoHideInput(innerView, imm);
            }
        }
    }


    /**
     * 生成地图导航查询uri的，其实用在http请求里也行
     */
    public static class MapParamsBuilder {
        StringBuilder mSB;//(-｡-;)额,这个变量名

        public MapParamsBuilder(String str) {
            mSB = new StringBuilder(str);
            if (mSB != null && !mSB.substring(mSB.length() - 1).equals("?")) {
                mSB.append("?");
            }
        }

        public MapParamsBuilder appendQueryParameter(String key, String value) {
            if (!mSB.substring(mSB.length() - 1).equals("?")) {
                mSB.append("&");
            }
            mSB.append(key + "=" + value);
            return this;
        }

        public String build() {
            return mSB.toString();
        }
    }

    /**
     * 获得屏幕横向像素
     *
     * @return
     */
    public static int getScreenWidth() {
        return BaseApplication.getApplication().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕纵向像素
     *
     * @return
     */
    public static int getScreenHeight() {
        return BaseApplication.getApplication().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDimensionPixelSize(int dimenId) {
        return BaseApplication.getApplication().getResources().getDimensionPixelSize(dimenId);
    }

    /**
     * DP转PX
     *
     * @param dp
     * @return
     */
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, BaseApplication.getApplication().getResources().getDisplayMetrics());
    }

    /**
     * 获得Drawable对象
     *
     * @param context
     * @param resId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }

    private static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    /**
     * 获得拨打电话的intent
     *
     * @param phoneNumber
     * @return
     */
    public static Intent getCallIntent(String phoneNumber) {
        StringBuffer sb = new StringBuffer("tel:");
        sb.append(phoneNumber);
        Intent contactIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(sb.toString()));
        return contactIntent;
    }

    /**
     * 设置按钮样式，statusCode来设置（设计产品时要考虑复用性啊大哥）
     * <P>(Button此刻要唱歌:
     * <p>美丽极限 爱漂亮没有终点
     * <p>追求完美的境界 人不爱美天诛地灭
     * <p>别气馁 旧观念抛到一边
     * <p>现在就开始改变 麻雀也能飞上青天
     * <p>http://music.163.com/#/song?id=210045 ）</p>
     *
     * @param buttonTag 按钮样式标记:{@link Constant#BUTTON_NORMAL}, {@link Constant#BUTTON_TEXT}
     * @param enable    按钮是否可点击
     */
//    private void setButtonStyle(Button button, int buttonTag, boolean enable) {
//        button.setBackgroundResource(buttonTag == Constant.BUTTON_NORMAL ?
//                R.drawable.ripple_activity_walkthrough_sign_up_button : null);
//        button.setTextColor(buttonTag == Constant.BUTTON_NORMAL ?
//                BaseApplication.getApplication().getResources().getColor(R.color.text_white) :
//                BaseApplication.getApplication().getResources().getColor(R.color.app_main_color));
//        button.setEnabled(enable);
//    }

    /**
     * 用户是否绑定过手机
     *
     * @return
     */
//    public static boolean hasBindedPhone() {
//        return SPUtils.getUserInfoEntity().getPhone() != null && !SPUtils.getUserInfoEntity().getPhone().isEmpty();
//    }

    /**
     * 判断手机是否安装某款应用
     *
     * @param appPackageName
     * @return
     */
    public static boolean isAppInstalled(String appPackageName) {
        try {
            BaseApplication.getApplication().getPackageManager().getPackageInfo(appPackageName,
                    PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
