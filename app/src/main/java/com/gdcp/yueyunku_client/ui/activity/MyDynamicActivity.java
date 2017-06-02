package com.gdcp.yueyunku_client.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.MyDynamicItemAdapter;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.presenter.MyDynamicPresenter;
import com.gdcp.yueyunku_client.presenter.impl.MyDynamicPresenterImpl;
import com.gdcp.yueyunku_client.view.MyDynamicView;
import com.gdcp.yueyunku_client.widget.FullyLinearLayoutManager;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asus on 2017/5/24.
 */

public class MyDynamicActivity extends BaseActivity implements MyDynamicView {
    private static final int IMAGE_CODE = 1001;
    @BindView(R.id.collasing)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.my_dynamic_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.rv_my_dynamic)
    XRecyclerView mRvMyDynamic;

    private List<Dynamic> dataList=new ArrayList<>();
    private MyDynamicItemAdapter mAdapter;

    private MyDynamicPresenter mPresenter;
    private String imgUrl=null;

    private String lastCreateAt=null;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbarLayout.setTitle(getString(R.string.my_dynamic));
        mPresenter = new MyDynamicPresenterImpl(this);

        mAdapter=new MyDynamicItemAdapter(this,dataList,this);
        FullyLinearLayoutManager layoutManager=new FullyLinearLayoutManager(this);
        mRvMyDynamic.setLayoutManager(layoutManager);
        mRvMyDynamic.setPullRefreshEnabled(false);
        mRvMyDynamic.setLoadingMoreEnabled(true);
        mRvMyDynamic.setLoadingListener(mListener);
        mRvMyDynamic.setAdapter(mAdapter);

        mPresenter.onLoadMyDynamic();
    }

    XRecyclerView.LoadingListener mListener=new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {

        }

        @Override
        public void onLoadMore() {
            mPresenter.onLoadMoreDynamic(lastCreateAt);
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_dynamic;
    }

    @Override
    public void onSetConfig(String backgroundUrl,List<Dynamic> list) {
        if (backgroundUrl!=null){
            Glide.with(this).load(backgroundUrl).into(mIvBg);
        }
        dataList.clear();
        dataList.addAll(list);
        lastCreateAt=list.get(list.size()-1).getCreatedAt();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUploadHeadSuccess() {
        toast(getString(R.string.update_success));
        mPresenter.onLoadMyDynamic();
    }

    @Override
    public void onUploadHeadFail() {
        toast(getString(R.string.update_fail));
    }

    @Override
    public void onDelDynamic(final Dynamic dynamic) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("删除该动态？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.onDelDynamic(dynamic);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDelSuccess() {
        toast(getString(R.string.del_success));
        mPresenter.onLoadMyDynamic();
        Common.isStartForYdc=true;
    }

    @Override
    public void onDelFail() {
        toast(getString(R.string.del_fail));
        mPresenter.onLoadMyDynamic();
    }

    @Override
    public void onLoadMoreDataSuccess(List<Dynamic> list) {
        if (list.size()==0){
            toast(getString(R.string.no_more_data));
            mRvMyDynamic.loadMoreComplete();
            return;
        }
        dataList.addAll(list);
        lastCreateAt=list.get(list.size()-1).getCreatedAt();
        mAdapter.notifyDataSetChanged();
        mRvMyDynamic.loadMoreComplete();
    }


    @OnClick(R.id.iv_bg)
    public void onClick() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("更换封面");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectImage();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IMAGE_CODE) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};

            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = managedQuery(uri, proj, null, null, null);

            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            imgUrl = cursor.getString(column_index);

            //上传背景图片
            mPresenter.onUploadHead(imgUrl);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
