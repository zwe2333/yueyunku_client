package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/14.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.edt_search)
    ClearEditText mEdtSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }


    @OnClick(R.id.tv_cancel)
    public void onClick() {
        finish();
    }
}
