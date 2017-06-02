package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.MoreSpaceAdapter;
import com.gdcp.yueyunku_client.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/30.
 */

public class MoreSpaceActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_space)
    RecyclerView mRvSpace;

    private MoreSpaceAdapter mAdapter;
    private List<User> mUsers=new ArrayList<>();
    private LinearLayoutManager mManager;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("更多场所");


        mManager=new LinearLayoutManager(this);
        mAdapter=new MoreSpaceAdapter(this,mUsers);
        mRvSpace.setLayoutManager(mManager);
        mRvSpace.setAdapter(mAdapter);

        loadBusiness();
    }

    private void loadBusiness() {
        BmobQuery<User> query=new BmobQuery<>();
        query.addWhereEqualTo("type",1);
        query.order("createdAt");
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    mUsers.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }else {
                    toast("加载失败");
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_more_space;
    }

}
