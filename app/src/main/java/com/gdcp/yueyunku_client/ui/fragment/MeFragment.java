package com.gdcp.yueyunku_client.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.LoginEvent;
import com.gdcp.yueyunku_client.event.SaveMsgEvent;
import com.gdcp.yueyunku_client.presenter.MePresenter;
import com.gdcp.yueyunku_client.presenter.impl.MePresenterImpl;
import com.gdcp.yueyunku_client.ui.activity.LoginActivity;
import com.gdcp.yueyunku_client.ui.activity.MyCollectionActivity;
import com.gdcp.yueyunku_client.ui.activity.MyDynamicActivity;
import com.gdcp.yueyunku_client.ui.activity.MySportActivity;
import com.gdcp.yueyunku_client.ui.activity.PersonInformationActivity;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.MeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class MeFragment extends BaseFragment implements MeView {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.ll_check_logined)
    LinearLayout mLlCheckLogined;
    @BindView(R.id.ll_user_setting)
    LinearLayout mLlUserSetting;
    @BindView(R.id.tv_user_name)
    TextView mTvUsername;
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.ll_my_dynamic)
    LinearLayout mLlMyDynamic;
    @BindView(R.id.ll_my_sport)
    LinearLayout mLlMySport;
    @BindView(R.id.ll_my_collection)
    LinearLayout mLlCollection;

    private MePresenter mPresenter;

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        mPresenter = new MePresenterImpl(this);
        mTvToolTitle.setText(getString(R.string.me));
        checkStatus();
    }

    private void checkStatus() {
        if (Common.ISLOGIN) {
            mLlUserSetting.setVisibility(View.VISIBLE);
            if (Common.headUrl != null) {
                Glide.with(getActivity()).load(Common.headUrl).into(mImgHead);
            } else {
                mImgHead.setImageResource(R.mipmap.logo2);
            }
            mTvUsername.setText(Common.username);
        } else {
            mLlUserSetting.setVisibility(View.GONE);
            mImgHead.setImageResource(R.mipmap.logo2);
            mTvUsername.setText(getString(R.string.no_login));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.ll_check_logined, R.id.ll_user_setting,R.id.ll_my_dynamic,R.id.ll_my_sport,R.id.ll_my_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_logined:
                if (Common.ISLOGIN) {
                    startActivity(PersonInformationActivity.class, false);
                } else {
                    startActivity(LoginActivity.class, false);
                }
                break;
            case R.id.ll_user_setting:
                if (Common.ISLOGIN) {
                    showAlertDialog();
                }
                break;
            case R.id.ll_my_dynamic:
                if (Common.ISLOGIN){
                    startActivity(MyDynamicActivity.class,false);
                }else {
                    startActivity(LoginActivity.class,false);
                }
                break;
            case R.id.ll_my_sport:
                if (Common.ISLOGIN){
                    startActivity(MySportActivity.class,false);
                }else {
                    startActivity(LoginActivity.class,false);
                }
                break;
            case R.id.ll_my_collection:
                if (Common.ISLOGIN){
                    startActivity(MyCollectionActivity.class,false);
                }else {
                    startActivity(LoginActivity.class,false);
                }
                break;
        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("退出登录");
        dialog.setMessage("确定退出当前账号？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.onLogout();
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
    public void onLogoutSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.logout_success));
                Common.ISLOGIN = false;
                //重新检查登录状态
                checkStatus();
            }
        });
    }


    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (event.isLogin()) {
            checkStatus();
        }
    }

    @Subscribe
    public void onMsgSave(SaveMsgEvent event) {
        if (event.isSuccess()) {
            checkStatus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
