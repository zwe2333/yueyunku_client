package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.PlacePresenter;
import com.gdcp.yueyunku_client.view.PlaceView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/30.
 */

public class PlacePresenterImpl implements PlacePresenter{
    private PlaceView mView;
    public PlacePresenterImpl(PlaceView view){
        mView=view;
    }

    @Override
    public void onGetPlaceMsg() {
        String id=mView.getId();
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("objectId",id);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    mView.onQueryBusinessSuccess(list.get(0));
                }else {
                    mView.onQueryBusinessFail();
                }
            }
        });

    }

    @Override
    public void onLoadSportType(User user) {
        BmobQuery<Space> query=new BmobQuery<>();
        query.addWhereEqualTo("business",user);
        query.include("business");
        query.findObjects(new FindListener<Space>() {
            @Override
            public void done(List<Space> list, BmobException e) {
                if (e==null){
                    mView.onGetPlaceSuccess(list);
                }else {
                    mView.onGetPlaceFail();
                }
            }
        });
    }
}
