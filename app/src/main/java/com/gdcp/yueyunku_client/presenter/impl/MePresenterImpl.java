package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.MePresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.MeView;

import cn.bmob.v3.BmobUser;

/**
 * Created by Asus on 2017/5/11.
 */

public class MePresenterImpl implements MePresenter{
    private MeView mView;
    public MePresenterImpl(MeView view){
        mView=view;
    }

    @Override
    public void onLogout() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                BmobUtils.logout();
                mView.onLogoutSuccess();
            }
        });
    }
}
