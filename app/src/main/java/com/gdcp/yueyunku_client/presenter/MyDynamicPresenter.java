package com.gdcp.yueyunku_client.presenter;

import com.gdcp.yueyunku_client.model.Dynamic;

/**
 * Created by Asus on 2017/5/24.
 */

public interface MyDynamicPresenter {
    void onLoadMyDynamic();

    void onUploadHead(String imgUrl);

    void onDelDynamic(Dynamic dynamic);

    void onLoadMoreDynamic(String lastCreateAt);
}
