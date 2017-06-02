package com.gdcp.yueyunku_client.view;

/**
 * Created by Asus on 2017/5/11.
 */

public interface RegisterView {
    void onCheckNumberSuccess(boolean isStart);

    void onCheckNumberError();

    void onSendRequestSuccess();

    void onPwdNullError();

    void onPwdNoSameError();

    void onPwdLengthError();

    void onRegisterSuccess();

    void onRegisterFail();

    void onSendRequestFail();
}
