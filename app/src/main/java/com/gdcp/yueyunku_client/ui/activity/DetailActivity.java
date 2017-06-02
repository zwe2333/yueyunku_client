package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Activity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_activity)
    ImageView mIvActivity;
    @BindView(R.id.tv_name_activity)
    TextView mTvNameActivity;
    @BindView(R.id.tv_intro_activity)
    TextView mTvIntroActivity;
    @BindView(R.id.tv_time_activity)
    TextView mTvTimeActivity;
    @BindView(R.id.tv_rule_activity)
    TextView mTvRuleActivity;
    @BindView(R.id.tv_type_jiangping)
    TextView mTvTypeJiangping;
    @BindView(R.id.tv_num_jiangping)
    TextView mTvNumJiangping;
    @BindView(R.id.tv_time_kaijiang)
    TextView mTvTimeKaijiang;
    @BindView(R.id.gone_linearLayout)
    LinearLayout mGoneLinearLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Activity activity = (Activity) getIntent().getSerializableExtra("huodong");
        mTvToolTitle.setText(activity.getBusiness().getUsername());
        mTvNameActivity.setText(activity.getName());
        mTvIntroActivity.setText(activity.getActivityIntro());
        mTvTimeActivity.setText(activity.getBeginTime() + "-" + activity.getEndTime());
        mTvRuleActivity.setText(activity.getRule());
        String type = activity.getTypeJiangping();
        String jiangpingNum = activity.getJiangpingNum();
        String kaijiangTime = activity.getKaijiangTime();
        if (type != null) {
            mGoneLinearLayout.setVisibility(View.VISIBLE);
            mTvTypeJiangping.setText(type);
            mTvNumJiangping.setText(jiangpingNum);
            mTvTimeKaijiang.setText(kaijiangTime);
        }
        Glide.with(this).load(activity.getPicUrl()).into(mIvActivity);
    }
}
