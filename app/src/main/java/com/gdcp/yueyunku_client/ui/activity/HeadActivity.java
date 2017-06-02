package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/17.
 */

public class HeadActivity extends BaseActivity {
    @BindView(R.id.img_head)
    ImageView mImgHead;

    @Override
    protected void init() {
        String url=getIntent().getStringExtra("head_url");
        Glide.with(this).load(url).into(mImgHead);
    }

    @Override
    protected void setWindowFuture() {
        super.setWindowFuture();
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_head;
    }


    @OnClick(R.id.img_head)
    public void onClick() {
        finish();
    }
}
