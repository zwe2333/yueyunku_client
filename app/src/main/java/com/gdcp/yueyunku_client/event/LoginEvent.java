package com.gdcp.yueyunku_client.event;

import com.gdcp.yueyunku_client.model.User;

/**
 * Created by Asus on 2017/5/12.
 */

public class LoginEvent {


    public LoginEvent(boolean isLogin){
        this.isLogin=isLogin;
    }

    boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }
}
