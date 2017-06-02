package com.gdcp.yueyunku_client.loader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yzs.imageshowpickerview.ImageLoader;

/**
 * Created by Asus on 2017/5/14.
 */

public class DynamicPicLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, String s, ImageView imageView) {
        Glide.with(context).load(s).into(imageView);
    }

    @Override
    public void displayImage(Context context, @DrawableRes Integer integer, ImageView imageView) {
        imageView.setImageResource(integer);
    }

    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
