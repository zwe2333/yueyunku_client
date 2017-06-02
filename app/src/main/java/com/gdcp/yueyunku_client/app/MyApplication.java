package com.gdcp.yueyunku_client.app;

import android.app.Application;


import org.litepal.LitePalApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by Asus on 2017/5/11.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "fc17e13ac860610cf084c738f247acad");
        LitePalApplication.initialize(this);
    }
}
