package com.gdcp.yueyunku_client.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.ViewPageAdapter;
import com.gdcp.yueyunku_client.presenter.ImageBrowserPresenter;
import com.gdcp.yueyunku_client.presenter.impl.ImageBrowserPresenterImpl;
import com.gdcp.yueyunku_client.view.ImageBrowserView;
import com.gdcp.yueyunku_client.widget.ViewPagerFixed;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Asus on 2017/5/14.
 */

public class ImageBrowserActivity extends BaseActivity implements ImageBrowserView,ViewPager.OnPageChangeListener {
    @BindView(R.id.view_page)
    ViewPagerFixed mViewPage;
    @BindView(R.id.tv_hint)
    TextView mTvHint;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    private ImageBrowserPresenter mPresenter;


    private ViewPageAdapter adapter;
    @Override
    protected void init() {
        mPresenter = new ImageBrowserPresenterImpl(this);
        mPresenter.loadImage();
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
        return R.layout.activity_image_browser;
    }


    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public void setImageBrowse(List<String> images, int position) {
        if(adapter == null && images != null && images.size() != 0){
            adapter = new ViewPageAdapter(this,images);
            mViewPage.setAdapter(adapter);
            mViewPage.setCurrentItem(position);
            mViewPage.addOnPageChangeListener(this);
            mTvHint.setText(position + 1 + "/" + images.size());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setPosition(position);
        mTvHint.setText(position + 1 + "/" + mPresenter.getImage().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
