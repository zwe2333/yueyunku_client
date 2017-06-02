package com.gdcp.yueyunku_client.ui.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/24.
 */

public class ShowAreaActivity extends BaseActivity {
    @BindView(R.id.ll_choose)
    LinearLayout mLlChoose;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_location_area)
    TextView mTvLocationArea;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("地区");
        String area=getIntent().getStringExtra("area");
        mTvLocationArea.setText(area);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_show_area;
    }



    @OnClick(R.id.ll_choose)
    public void onClick() {
        startActivity(ChooseAreaActivity.class,true);
    }
}
