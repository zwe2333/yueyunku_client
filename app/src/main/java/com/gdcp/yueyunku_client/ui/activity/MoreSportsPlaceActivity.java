package com.gdcp.yueyunku_client.ui.activity;

/**
 * Created by Asus on 2017/5/18.
 */

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.MoreSportsPlaceAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreSportsPlaceActivity extends BaseActivity {

    /*
    *
    * 作废的类
    * */

    @BindView(R.id.rv_place_sport)
    RecyclerView rvPlaceSport;
    @BindView(R.id.rb_order_zonghe)
    RadioButton rbOrderZonghe;
    @BindView(R.id.arrow1)
    ImageView arrow1;
    @BindView(R.id.rb_way_sport)
    RadioButton rbWaySport;
    @BindView(R.id.arrow2)
    ImageView arrow2;
    @BindView(R.id.rb_shaixuan)
    RadioButton rbShaixuan;
    @BindView(R.id.arrow3)
    ImageView arrow3;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private MoreSportsPlaceAdapter moreSportsPlaceAdapter;
    private boolean isListViewUp = false;
    private ArrayList list;
    private ArrayAdapter arrayAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_more_sports_place;
    }

    @Override
    protected void init() {
        moreSportsPlaceAdapter = new MoreSportsPlaceAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPlaceSport.setLayoutManager(linearLayoutManager);
        rvPlaceSport.setAdapter(moreSportsPlaceAdapter);
        list = new ArrayList();
        arrayAdapter = new ArrayAdapter<>(MoreSportsPlaceActivity.this, R.layout.list_item, list);
        listView.setAdapter(arrayAdapter);
    }

    private void clearTextColor() {
        rbOrderZonghe.setTextColor(getResources().getColor(R.color.black));
        rbWaySport.setTextColor(getResources().getColor(R.color.black));
        rbShaixuan.setTextColor(getResources().getColor(R.color.black));
    }

    private void clearArrow() {
        arrow1.setImageResource(R.mipmap.icon_spinner_drop_pre);
        arrow2.setImageResource(R.mipmap.icon_spinner_drop_pre);
        arrow3.setImageResource(R.mipmap.icon_spinner_find_pre);
    }


    @OnClick({R.id.rb_order_zonghe, R.id.rb_way_sport, R.id.rb_shaixuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_order_zonghe:
                setZongheData();
                clearArrow();
                clearTextColor();
                rbOrderZonghe.setTextColor(getResources().getColor(R.color.colorDarkorange));
                arrow1.setImageResource(R.mipmap.icon_spinner_drop_down);
                isListViewUp = !isListViewUp;
                if (isListViewUp) {
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                }

                break;
            case R.id.rb_way_sport:
                setWaysportData();
                clearArrow();
                clearTextColor();
                rbWaySport.setTextColor(getResources().getColor(R.color.colorDarkorange));
                arrow2.setImageResource(R.mipmap.icon_spinner_drop_down);
                isListViewUp = !isListViewUp;
                if (isListViewUp) {
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_shaixuan:
                clearArrow();
                clearTextColor();
                rbShaixuan.setTextColor(getResources().getColor(R.color.colorDarkorange));
                arrow3.setImageResource(R.mipmap.icon_spinner_drop_down);
                listView.setVisibility(View.GONE);
                drawerLayout.openDrawer(GravityCompat.END);
                break;
        }
    }

    private void setZongheData() {
        list.clear();
        list.add("综合排序");
        list.add("热度排序");
        list.add("好评排序");
        arrayAdapter.notifyDataSetChanged();
    }

    private void setWaysportData() {
        list.clear();
        list.add("篮球");
        list.add("排球");
        list.add("足球");
        list.add("网球");
        list.add("羽毛球");
        list.add("健身");
        list.add("其它");
        arrayAdapter.notifyDataSetChanged();
    }



}

