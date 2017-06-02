package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.MyCollectionAdapter;
import com.gdcp.yueyunku_client.event.CancelCollectEvent;
import com.gdcp.yueyunku_client.model.Collection;
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.utils.BmobUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Asus on 2017/5/31.
 */

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_my_collection)
    RecyclerView mRvMyCollection;

    private List<Space> mCollections=new ArrayList<>();
    private MyCollectionAdapter mAdapter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("我的收藏");

        EventBus.getDefault().register(this);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        mRvMyCollection.setLayoutManager(layoutManager);
        mAdapter=new MyCollectionAdapter(this,mCollections);
        mRvMyCollection.setAdapter(mAdapter);
        loadCollectionData();
    }

    private void loadCollectionData() {
        mCollections.clear();
        BmobQuery<Collection> query=new BmobQuery<>();
        query.addWhereEqualTo("collector", BmobUtils.getCurrentUser());
        query.order("-createdAt");
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        querySpace(list.get(i).getObId());
                        if (i==list.size()-1){
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    toast("加载失败");
                }
            }
        });
    }

    private void querySpace(String obId) {
        BmobQuery<Space> query=new BmobQuery<>();
        query.include("business");
        query.getObject(obId, new QueryListener<Space>() {
            @Override
            public void done(Space space, BmobException e) {
                if (e==null){
                    mCollections.add(space);
                    mAdapter.notifyDataSetChanged();
                }else {
                    toast("加载失败");
                }
            }
        });

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_collection;
    }


    @Subscribe
    public void cancelEvent(CancelCollectEvent event){
        if (event.isCancel()){
            loadCollectionData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
