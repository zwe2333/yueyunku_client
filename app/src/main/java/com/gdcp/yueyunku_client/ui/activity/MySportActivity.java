package com.gdcp.yueyunku_client.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.MySportAdapter;
import com.gdcp.yueyunku_client.model.Order;
import com.gdcp.yueyunku_client.utils.BmobUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/30.
 */

public class MySportActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_my_sport)
    RecyclerView mRvMySport;

    private List<Order> mOrders=new ArrayList<>();
    private MySportAdapter mAdapter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("我的运动");


        mAdapter=new MySportAdapter(this,mOrders);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRvMySport.setLayoutManager(manager);
        mRvMySport.setAdapter(mAdapter);

        loadData();
    }

    private void loadData() {
        BmobQuery<Order> query=new BmobQuery<>();
        query.addWhereEqualTo("joiner", BmobUtils.getCurrentUser());
        query.order("-createdAt");
        query.include("joiner");
        query.include("business");
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                mOrders.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_sport;
    }

}
