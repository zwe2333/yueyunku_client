package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.LoginEvent;
import com.gdcp.yueyunku_client.event.SaveMsgEvent;
import com.gdcp.yueyunku_client.factory.FragmentFactory;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.MainPresenter;
import com.gdcp.yueyunku_client.presenter.impl.MainPresenterImpl;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.MainView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private FragmentManager mFragmentManager;

    private MainPresenter mPresenter;

    private long exitTime = 0;
    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        mPresenter=new MainPresenterImpl(this);
        mFragmentManager = getSupportFragmentManager();
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
        mBottomBar.setOnTabReselectListener(mReselectListener);
        //检查登录状态
        mPresenter.onCheckLogin();

    }

    private OnTabReselectListener mReselectListener=new OnTabReselectListener() {
        @Override
        public void onTabReSelected(@IdRes int tabId) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_contain, FragmentFactory.getInstance().getFragment(tabId)).commit();
        }
    };

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_contain, FragmentFactory.getInstance().getFragment(tabId)).commit();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event){
        if (event.isLogin()){
            mPresenter.onCheckLogin();
        }
    }

    @Subscribe
    public void onSaveEvent(SaveMsgEvent event){
        if (event.isSuccess()){
            mPresenter.onCheckLogin();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onLoginStateCallBack(final boolean isLogin) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isLogin){
                        toast(getString(R.string.no_login));
                    }else {
                        Common.ISLOGIN=true;
                        Common.username=BmobUtils.getCurrentUser().getUsername();
                        Common.headUrl=BmobUtils.getCurrentUser().getHead();
                    }
                    Common.ISLOGIN=isLogin;
                }
            });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                toast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
