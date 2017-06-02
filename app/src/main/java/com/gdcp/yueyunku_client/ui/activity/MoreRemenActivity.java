package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.HuodongAdapter;
import com.gdcp.yueyunku_client.model.Activity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MoreRemenActivity extends BaseActivity {


    @BindView(R.id.rv_activity_remen)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private HuodongAdapter mHuodongAdapter;
    private List<Activity> mActivities = new ArrayList<>();

    private String lastCreateAt=null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_more_remen;
    }

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("更多活动");


        mHuodongAdapter = new HuodongAdapter(mActivities, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mXRecyclerView.setLayoutManager(linearLayoutManager);
        mXRecyclerView.setAdapter(mHuodongAdapter);
        mXRecyclerView.setLoadingListener(mListener);

        loadData();
    }

    XRecyclerView.LoadingListener mListener=new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            loadData();
        }

        @Override
        public void onLoadMore() {
            loadMoreData();
        }
    };

    private void loadMoreData() {
        BmobQuery<Activity> query = new BmobQuery<>();
        query.include("business");
        query.order("-createdAt");
        query.setLimit(5);
        Date date  = null;
        if (lastCreateAt!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastCreateAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        toast("没有更多数据");
                        return;
                    }else {
                        mActivities.addAll(list);
                        mXRecyclerView.loadMoreComplete();
                        lastCreateAt=list.get(list.size()-1).getCreatedAt();
                        mHuodongAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void loadData() {
        BmobQuery<Activity> query = new BmobQuery<>();
        query.include("business");
        query.order("-createdAt");
        query.setLimit(10);
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e == null) {
                    mActivities.clear();
                    mActivities.addAll(list);
                    lastCreateAt=list.get(list.size()-1).getCreatedAt();
                    mXRecyclerView.refreshComplete();
                    mHuodongAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
