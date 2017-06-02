package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.LoginPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.LoginView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Asus on 2017/5/11.
 */

public class LoginPresenterImpl implements LoginPresenter{
    private LoginView mView;
    private boolean isSuccess;
    public LoginPresenterImpl(LoginView view){
        mView=view;
    }

    @Override
    public void onLogin(final String phoneNumber, final String pwd) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                BmobUtils.login(phoneNumber, pwd, new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e==null){
                            isSuccess=true;
                            mView.onLoginSuccess(isSuccess);
                        }else {
                            isSuccess=false;
                            mView.onLoginFail(isSuccess);
                        }
                    }
                });
            }
        });

    }
}
