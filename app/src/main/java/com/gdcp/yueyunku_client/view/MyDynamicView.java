package com.gdcp.yueyunku_client.view;

import com.gdcp.yueyunku_client.model.Dynamic;

import java.util.List;

/**
 * Created by Asus on 2017/5/24.
 */

public interface MyDynamicView {
    void onSetConfig(String backgroundUrl,List<Dynamic> list);

    void onUploadHeadSuccess();

    void onUploadHeadFail();

    void onDelDynamic(Dynamic dynamic);

    void onDelSuccess();

    void onDelFail();

    void onLoadMoreDataSuccess(List<Dynamic> list);
}
