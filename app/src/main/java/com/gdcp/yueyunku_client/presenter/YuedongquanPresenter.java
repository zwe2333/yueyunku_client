package com.gdcp.yueyunku_client.presenter;

import android.widget.ImageView;

import com.gdcp.yueyunku_client.model.Dynamic;

import java.util.List;

/**
 * Created by Asus on 2017/5/11.
 */

public interface YuedongquanPresenter {
    void onLoadData();

    void onCheckZanIfHasDone(Dynamic dynamic);

    void onMakeZan(Dynamic dynamic);

    void onSetZanLength(Dynamic dynamic);

    void onLoadMoreData(String lastCreateAt);

    void onPullToLoadData();

}
