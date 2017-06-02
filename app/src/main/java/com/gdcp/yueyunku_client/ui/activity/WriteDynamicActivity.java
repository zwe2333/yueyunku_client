package com.gdcp.yueyunku_client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.PostSuccessEvent;
import com.gdcp.yueyunku_client.loader.DynamicPicLoader;
import com.gdcp.yueyunku_client.model.ImageBean;
import com.gdcp.yueyunku_client.presenter.WriteDynamicPresenter;
import com.gdcp.yueyunku_client.presenter.impl.WriteDynamicPresenterImpl;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.WriteDynamicView;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.yzs.imageshowpickerview.ImageShowPickerListener;
import com.yzs.imageshowpickerview.ImageShowPickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Asus on 2017/5/14.
 */

public class WriteDynamicActivity extends BaseActivity implements WriteDynamicView {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_content_dongtai)
    EditText mContentDongtai;

    @BindView(R.id.imageShowPickerView)
    ImageShowPickerView imageShowPickerView;

    private List<ImageBean> listImage;
    private WriteDynamicPresenter mPresenter;
    private int max = 9;
    private ActionBar mActionBar;

    private ArrayList<String> photos=new ArrayList<>();


    @Override
    protected void init() {
        //title部分
        mTvToolTitle.setText(getString(R.string.sendMotion));
        mTvSend.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new WriteDynamicPresenterImpl(this);

        //图片选择器部分
        listImage = new ArrayList<ImageBean>();
        imageShowPickerView.setImageLoaderInterface(new DynamicPicLoader());
        imageShowPickerView.setNewData(listImage);
        imageShowPickerView.setPickerListener(mShowPickerListener);
        imageShowPickerView.show();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_write_dynamic;
    }

    ImageShowPickerListener mShowPickerListener = new ImageShowPickerListener() {
        @Override
        public void addOnClickListener(int i) {
            PhotoPicker.builder()
                    .setPhotoCount(max)
                    .setShowCamera(true)
                    .setShowGif(true)
                    .setPreviewEnabled(false)
                    .start(WriteDynamicActivity.this, PhotoPicker.REQUEST_CODE);
        }

        @Override
        public void picOnClickListener(List<ImageShowPickerBean> list, int i, int i1) {

        }

        @Override
        public void delOnClickListener(int i, int i1) {
            listImage.remove(i);
            max++;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                max -= photos.size();
                for (int i = 0; i < photos.size(); i++) {
                    listImage.add(new ImageBean(photos.get(i)));
                    imageShowPickerView.addData(new ImageBean(photos.get(i)));
                }
            }
        }
    }

    @OnClick(R.id.tv_send)
    public void onClick() {
        String content=mContentDongtai.getText().toString().trim();
        showProgress(getString(R.string.posting));

        mPresenter.onUploadDynamic(content,photos);

    }

    @Override
    public void onUploadFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.content_no_null));
            }
        });

    }


    @Override
    public void onUploadSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.send_success));
                EventBus.getDefault().post(new PostSuccessEvent(true));
                finish();
            }
        });
    }

    @Override
    public void onContentNullError() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.content_no_null));
            }
        });

    }
}
