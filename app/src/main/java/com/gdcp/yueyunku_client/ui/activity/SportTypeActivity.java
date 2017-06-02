package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.SportSpaceAdapter;
import com.gdcp.yueyunku_client.model.Space;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/31.
 */

public class SportTypeActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_sport_type)
    RecyclerView mRvSportType;

    private List<Space> mSpaces=new ArrayList<>();
    private SportSpaceAdapter mAdapter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getSportType());

        GridLayoutManager manager=new GridLayoutManager(this,2);
        mAdapter=new SportSpaceAdapter(this,mSpaces);
        mRvSportType.setLayoutManager(manager);
        mRvSportType.setAdapter(mAdapter);
        querySpace();
    }

    private void querySpace() {
        String sportType=getSportType();
        BmobQuery<Space> query=new BmobQuery<>();
        query.addWhereEqualTo("type",sportType);
        query.include("business");
        query.findObjects(new FindListener<Space>() {
            @Override
            public void done(List<Space> list, BmobException e) {
                if (e==null){
                    mSpaces.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public String getSportType() {
        return getIntent().getStringExtra("sport_type_for_card");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sport_type;
    }

}
