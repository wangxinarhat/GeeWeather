package wang.wangxinarhat.geeweather.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wang.wangxinarhat.geeweather.R;
import wang.wangxinarhat.geeweather.global.BaseApplication;


/**
 * Created by Fesen on 2015/7/4.
 */
public class MyToast {

    private static volatile CustomToast sToast;

    public static CustomToast getToast() {
        if (sToast == null) {
            synchronized (MyToast.class) {
                if (sToast == null) {
//                    sToast = Toast.makeText(BaseApplication.getApplication(), "", Toast.LENGTH_SHORT);
                    sToast = new CustomToast(BaseApplication.getApplication());
                }
            }
        }
        return sToast;
    }

    /**
     * 显示短时间的toast
     *
     * @param str
     */
    public static void showShortToast(String str) {
        getToast().setDuration(Toast.LENGTH_SHORT);
        getToast().setText(str);
        getToast().show();
    }

    /**
     * 显示短时间的toast
     *
     * @param resId
     */
    public static void showShortToast(int resId) {
        getToast().setDuration(Toast.LENGTH_SHORT);
        getToast().setText(resId);
        getToast().show();
    }

    /**
     * 显示长时间的toast
     *
     * @param str
     */
    public static void showLongToast(String str) {
        getToast().setDuration(Toast.LENGTH_LONG);
        getToast().setText(str);
        getToast().show();
    }

    /**
     * 显示长时间的toast
     *
     * @param resId
     */
    public static void showLongToast(int resId) {
        getToast().setDuration(Toast.LENGTH_LONG);
        getToast().setText(resId);
        getToast().show();
    }

    public static void showConnectError() {
        showShortToast(R.string.network_error);
    }

    public static class CustomToast extends Toast {
        private TextView mTextView;

        /**
         * Construct an empty Toast object.  You must call {@link #setView} before you
         * can call {@link #show}.
         *
         * @param context The context to use.  Usually your {@link Application}
         *                or {@link Activity} object.
         */
        public CustomToast(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            mTextView = (TextView) view.findViewById(R.id.toast_custom_text);
//            try {
//                Typeface tp = Typeface.createFromFile(AppContent.FONT_NORMAL_PATH);
//                mTextView.setTypeface(tp);
//            } catch (Exception e) {
//                Log.e("wangqiujia", "Font is not exist");
//            }
            setView(view);
        }

        public void setText(int resId) {
            mTextView.setText(resId);
        }

        public void setText(String str) {
            mTextView.setText(str);
        }
    }
}
