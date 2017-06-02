package com.gdcp.yueyunku_client.view;

import com.gdcp.yueyunku_client.model.User;

import java.util.List;

/**
 * Created by Asus on 2017/5/11.
 */

public interface YueyundongView {


    void onLoadPlaceDataSuccess(List<User> list);

    void onLoadPlaceDataFail();
}
