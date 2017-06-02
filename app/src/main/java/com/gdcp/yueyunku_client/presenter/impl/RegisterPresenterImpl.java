package com.gdcp.yueyunku_client.presenter.impl;

import android.text.TextUtils;

import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.RegisterPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.StringUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.RegisterView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Asus on 2017/5/11.
 */

public class RegisterPresenterImpl implements RegisterPresenter{
    private RegisterView mView;
    public RegisterPresenterImpl(RegisterView view){
        mView=view;
    }

    private boolean isRightPhoneNumber(String phoneNumber){
        if (StringUtils.isMobileNo(phoneNumber)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onAcqNumber(final String phoneNumber) {
       if (isRightPhoneNumber(phoneNumber)){
            mView.onCheckNumberSuccess(true);
        }else {
            mView.onCheckNumberError();
            return;
        }
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                BmobUtils.sendCode(phoneNumber, new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e==null){
                            mView.onSendRequestSuccess();
                        }else {
                            mView.onSendRequestFail();
                        }
                    }
                });
            }
        });
    }

    private boolean isRightPwd(String password,String confirmPwd){
        if (TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPwd)){
            mView.onPwdNullError();
            return false;
        }
        if (password.length()<6||password.length()>16){
            mView.onPwdLengthError();
            return false;
        }
        if (password.length()!=confirmPwd.length()){
            mView.onPwdNoSameError();
            return false;
        }
        return true;
    }

    @Override
    public void onRegisterAccount(final String phoneNumber2, final String password, String confirmPwd, final String verificationCode) {
        if (isRightPhoneNumber(phoneNumber2)){
            mView.onCheckNumberSuccess(false);
        }else {
            mView.onCheckNumberError();
            return;
        }

        if (!isRightPwd(password,confirmPwd)){
            //密码不正确
            return;
        }

        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                BmobUtils.signByMobile(phoneNumber2, password, verificationCode, new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e==null){
                            mView.onRegisterSuccess();
                        }else {
                            mView.onRegisterFail();
                        }
                    }
                });
            }
        });

    }

}
