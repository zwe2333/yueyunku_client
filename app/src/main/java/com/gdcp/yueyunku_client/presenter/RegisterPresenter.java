package com.gdcp.yueyunku_client.presenter;

/**
 * Created by Asus on 2017/5/11.
 */

public interface RegisterPresenter {

    void onAcqNumber(String phoneNumber);

    void onRegisterAccount(String phoneNumber2, String password, String confirmPwd,String verificationCode);
}
