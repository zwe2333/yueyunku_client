package com.gdcp.yueyunku_client.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.presenter.WelcomePresenter;
import com.gdcp.yueyunku_client.presenter.impl.WelcomePresenterImpl;
import com.gdcp.yueyunku_client.view.WelcomeView;

import java.util.List;

/**
 * Created by Asus on 2017/5/10.
 */

public class WelcomeActivity extends OnboarderActivity implements WelcomeView{
    private WelcomePresenter mPresenter;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private boolean isFirstIn=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        isFirstIn=sp.getBoolean("isFirstIn",true);
        if (isFirstIn){
            isFirstIn=false;
            editor=sp.edit();
            editor.putBoolean("isFirstIn",isFirstIn);
            editor.apply();
        }else {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        mPresenter=new WelcomePresenterImpl(this);
        mPresenter.setConfig();
    }

    @Override
    public void onConfigSetted(List<OnboarderPage> pages) {
        setSkipButtonTitle(R.string.skip);
        setFinishButtonTitle(R.string.finish);

        setOnboardPagesReady(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        goTo(MainActivity.class,true);
    }

    private void goTo(Class activity, boolean isFinish) {
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }
}
