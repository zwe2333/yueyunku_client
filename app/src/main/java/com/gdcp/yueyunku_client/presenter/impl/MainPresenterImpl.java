package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.MainPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.MainView;

/**
 * Created by Asus on 2017/5/12.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mView;
    private boolean isLogin;
    public MainPresenterImpl(MainView view){
        mView=view;
    }

    @Override
    public void onCheckLogin() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                isLogin=BmobUtils.getCurrentUserState();
                mView.onLoginStateCallBack(isLogin);
            }
        });
    }

}
