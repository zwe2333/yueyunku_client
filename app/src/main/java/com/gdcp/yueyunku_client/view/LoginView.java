package com.gdcp.yueyunku_client.view;

/**
 * Created by Asus on 2017/5/11.
 */

public interface LoginView {
    void onLoginSuccess(boolean isSuccess);

    void onLoginFail(boolean isSuccess);

    void onNoBusinessUserLogin();
}
