package wang.wangxinarhat.geeweather.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import wang.wangxinarhat.geeweather.R;


public class GlideUtils {
    public static void loadImage(Context context, @IdRes int resId, ImageView imageView) {


        Glide.with(context)
                .load(resId)
                .crossFade()
                .dontAnimate()
                .placeholder(R.mipmap.ic_default)
                .into(imageView);

    }

    public static void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade()
                .dontAnimate()
                .placeholder(R.mipmap.ic_default)
                .into(imageView);
    }

    public static void loadImage(@IdRes int resId, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(resId)
                .crossFade()
                .dontAnimate()
                .placeholder(R.mipmap.ic_default)
                .into(imageView);
    }

    public static void loadAvatar(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .placeholder(R.mipmap.ic_default)
                .into(imageView);

    }

    public static void loadAvatar(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .dontAnimate()
                .placeholder(R.mipmap.ic_default)
                .into(imageView);

    }


}
