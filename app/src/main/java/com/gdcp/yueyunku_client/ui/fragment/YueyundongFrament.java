package com.gdcp.yueyunku_client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.HuodongAdapter;
import com.gdcp.yueyunku_client.adapter.PlaceAdapter;
import com.gdcp.yueyunku_client.adapter.YueyundongItemAdapter;
import com.gdcp.yueyunku_client.adapter.YydSportTypeAdapter;
import com.gdcp.yueyunku_client.loader.GlideImageLoader;
import com.gdcp.yueyunku_client.model.Activity;
import com.gdcp.yueyunku_client.model.Place;
import com.gdcp.yueyunku_client.model.SportType;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.YueyundongPresenter;
import com.gdcp.yueyunku_client.presenter.impl.YueyundongPresenterImpl;
import com.gdcp.yueyunku_client.ui.activity.MoreRemenActivity;
import com.gdcp.yueyunku_client.ui.activity.MoreSpaceActivity;
import com.gdcp.yueyunku_client.ui.activity.MoreSportsPlaceActivity;
import com.gdcp.yueyunku_client.ui.activity.SearchActivity;
import com.gdcp.yueyunku_client.view.YueyundongView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/10.
 */

public class YueyundongFrament extends BaseFragment implements YueyundongView {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_love)
    ImageView mIvLove;
    @BindView(R.id.rv_place_sport)
    RecyclerView mRvPlaceSport;
    @BindView(R.id.iv_remen)
    ImageView mIvRemen;
    @BindView(R.id.tv_more_sport_palce)
    TextView mTvMoreSportPalce;
    @BindView(R.id.rv_sport_type)
    RecyclerView rvSportType;
    @BindView(R.id.rv_activity_hot)
    RecyclerView mRvHot;
    @BindView(R.id.tv_more_activity)
    TextView tvMoreActivity;

    YueyundongItemAdapter mAdapter;


    private PlaceAdapter mPlaceAdapter;
    private List<User> mPlaces=new ArrayList<>();

    private YydSportTypeAdapter mYydSportTypeAdapter;
    private List<SportType> mTypes=new ArrayList<>();

    private HuodongAdapter mHuodongAdapter;
    private List<Activity> mActivities=new ArrayList<>();

    //模拟数据部分
    private Integer[] data = {R.mipmap.love_pic1, R.mipmap.love_pic2, R.mipmap.love_pic3, R.mipmap.love_pic4, R.mipmap.love_pic1, R.mipmap.love_pic1,
            R.mipmap.love_pic3, R.mipmap.love_pic4, R.mipmap.love_pic1, R.mipmap.love_pic1};


    private YueyundongPresenter mPresenter;

    @Override
    protected void init() {
        mPresenter = new YueyundongPresenterImpl(this);
        mTvToolTitle.setText(getString(R.string.arrange));


        mPlaceAdapter=new PlaceAdapter(mPlaces,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvPlaceSport.setLayoutManager(linearLayoutManager);
        mRvPlaceSport.setNestedScrollingEnabled(false);
        mRvPlaceSport.setAdapter(mPlaceAdapter);
        mPresenter.onLoadData();

        prepareSportType();
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        rvSportType.setLayoutManager(layoutManager);
        rvSportType.setHasFixedSize(true);
        rvSportType.setAdapter(new YydSportTypeAdapter(mTypes,getActivity()));
        rvSportType.addOnScrollListener(new CenterScrollListener());

        LinearLayoutManager activityManager=new LinearLayoutManager(getActivity());
        mHuodongAdapter=new HuodongAdapter(mActivities,getContext());
        mRvHot.setLayoutManager(activityManager);
        mRvHot.setAdapter(mHuodongAdapter);
        mRvHot.setNestedScrollingEnabled(false);

    }

    private void loadActivity() {
        mActivities.clear();
        BmobQuery<Activity> query=new BmobQuery<>();
        query.include("business");
        query.setLimit(10);
        query.order("-createdAt");
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> list, BmobException e) {
                if (e==null){
                    mActivities.addAll(list);
                    mHuodongAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void prepareSportType() {
        mTypes.add(new SportType(R.mipmap.basketball,"篮球"));
        mTypes.add(new SportType(R.mipmap.football,"足球"));
        mTypes.add(new SportType(R.mipmap.badminton,"羽毛球"));
        mTypes.add(new SportType(R.mipmap.volleyball,"排球"));
        mTypes.add(new SportType(R.mipmap.tennis,"网球"));
        mTypes.add(new SportType(R.mipmap.pingpong,"乒乓球"));
        mTypes.add(new SportType(R.mipmap.yoga,"瑜伽"));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_yueyundong;
    }

    @Override
    public void onLoadPlaceDataSuccess(List<User> list) {
        mPlaces.clear();
        mPlaces.addAll(list);
        mPlaceAdapter.notifyDataSetChanged();
        loadActivity();
    }

    @Override
    public void onLoadPlaceDataFail() {
        toast("加载失败");
    }

    @OnClick({R.id.tv_more_sport_palce,R.id.tv_more_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more_sport_palce:
                startActivity(MoreSpaceActivity.class,false);
                break;
            case R.id.tv_more_activity:
                startActivity(MoreRemenActivity.class,false);
                break;
        }
    }
}
