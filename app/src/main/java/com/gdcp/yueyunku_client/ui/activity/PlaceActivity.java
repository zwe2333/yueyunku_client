package com.gdcp.yueyunku_client.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.SportTypeAdapter;
import com.gdcp.yueyunku_client.model.Place;
import com.gdcp.yueyunku_client.model.Places;
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.PlacePresenter;
import com.gdcp.yueyunku_client.presenter.impl.PlacePresenterImpl;
import com.gdcp.yueyunku_client.view.PlaceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Asus on 2017/5/29.
 */

public class PlaceActivity extends BaseActivity implements PlaceView{
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.place_url)
    ImageView mPlaceUrl;
    @BindView(R.id.place_msg)
    TextView mTextView;
    @BindView(R.id.buss_name)
    TextView tvBuss;
    @BindView(R.id.rvSportType)
    RecyclerView mRecyclerView;


    /*
    * 场馆信息
    * */

    private PlacePresenter mPresenter;
    private List<Space> mSpaces=new ArrayList<>();
    private SportTypeAdapter mAdapter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText("场馆信息");
        mPresenter=new PlacePresenterImpl(this);
        mAdapter=new SportTypeAdapter(this,mSpaces);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.onGetPlaceMsg();
    }

    @Override
    public String getId(){
        return getIntent().getStringExtra("place");
    }

    @Override
    public void onGetPlaceSuccess(List<Space> list) {

        mSpaces.clear();
        mSpaces.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPlaceFail() {

    }

    @Override
    public void onQueryBusinessSuccess(User user) {
        if (user.getBackgroundUrl()!=null){
            Glide.with(this).load(user.getBackgroundUrl()).into(mPlaceUrl);
        }else {
            mPlaceUrl.setImageResource(R.mipmap.logo3);
        }
        tvBuss.setText(user.getUsername()+"("+user.getMobilePhoneNumber()+")");

        if (user.getPlaceIntro()!=null){
            mTextView.setText(user.getPlaceIntro());
        }
        mPresenter.onLoadSportType(user);
    }

    @Override
    public void onQueryBusinessFail() {
        toast("加载失败");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_place_msg;
    }

}
