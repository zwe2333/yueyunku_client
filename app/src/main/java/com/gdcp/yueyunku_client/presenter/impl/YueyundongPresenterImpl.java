package com.gdcp.yueyunku_client.presenter.impl;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Place;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.YueyundongPresenter;
import com.gdcp.yueyunku_client.view.YueyundongView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/11.
 */

public class YueyundongPresenterImpl implements YueyundongPresenter {
    private YueyundongView mView;

    public YueyundongPresenterImpl(YueyundongView view){
        mView=view;
    }


    @Override
    public void onLoadData() {
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("type",1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    mView.onLoadPlaceDataSuccess(list);
                }else {
                    mView.onLoadPlaceDataFail();
                }
            }
        });
    }
}
