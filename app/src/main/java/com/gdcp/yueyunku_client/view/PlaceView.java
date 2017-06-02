package com.gdcp.yueyunku_client.view;

import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.model.User;

import java.util.List;

/**
 * Created by Asus on 2017/5/30.
 */

public interface PlaceView {
    String getId();

    void onGetPlaceSuccess(List<Space> list);

    void onGetPlaceFail();

    void onQueryBusinessSuccess(User user);

    void onQueryBusinessFail();
}
