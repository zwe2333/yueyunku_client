package com.gdcp.yueyunku_client.presenter.impl;

import android.content.Intent;

import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.presenter.ImageBrowserPresenter;
import com.gdcp.yueyunku_client.view.ImageBrowserView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 2017/5/14.
 */

public class ImageBrowserPresenterImpl implements ImageBrowserPresenter{
    private ImageBrowserView mView;
    private ArrayList<String> images;
    private int position;
    public ImageBrowserPresenterImpl(ImageBrowserView view){
        mView=view;
    }

    @Override
    public void loadImage() {
        Intent intent=mView.getDataIntent();
        images=intent.getStringArrayListExtra(Common.IMAGES);
        position=intent.getIntExtra(Common.INDEX,0);
        mView.setImageBrowse(images,position);
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public List<String> getImage() {
        return images;
    }
}
