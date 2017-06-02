package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.ui.activity.ImageBrowserActivity;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus- on 2017/5/12.
 */

public class MyNineGridImageViewAdapter extends NineGridImageViewAdapter<String>{

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
        super.onItemImageClick(context, imageView, index, list);


        Intent intent=new Intent(context, ImageBrowserActivity.class);


        ArrayList<String> iList= new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            iList.add(list.get(i));
        }

        intent.putStringArrayListExtra(Common.IMAGES, iList);
        intent.putExtra(Common.INDEX,index);
        context.startActivity(intent);
    }

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String path) {
        Glide.with(context).load(path).into(imageView);
    }
}
