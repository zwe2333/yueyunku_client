package com.gdcp.yueyunku_client.view;

import android.widget.ImageView;

import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;

import java.util.List;

/**
 * Created by Asus on 2017/5/11.
 */

public interface YuedongquanView {
    void onLoadDataSuccess(List<Dynamic> list);

    void onLoadDataFail();

    void onCheckZan(Dynamic dynamic);

    void onZanHasDone();

    void onMakeZanPro(Dynamic dynamic);

    void onRefreshZanLength();

    void onSetZan(Dynamic dynamic);

    void onLoadMoreDataSuccess(List<Dynamic> list);

    void onNoLogin();

    void onLoadMoreDataFail();
}
