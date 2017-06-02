package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.LoginEvent;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.LoginPresenter;
import com.gdcp.yueyunku_client.presenter.impl.LoginPresenterImpl;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.LoginView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class LoginActivity extends BaseActivity implements LoginView{
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.tv_forgetpwd)
    TextView mTvForgetpwd;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private LoginPresenter mPresenter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.login));

        mPresenter=new LoginPresenterImpl(this);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.tv_forgetpwd, R.id.tv_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgetpwd:
                startActivity(ForgetPwdActivity.class,false);
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class, false);
                break;
            case R.id.btn_login:
                String phoneNumber=mEdtPhone.getText().toString().trim();
                String pwd=mEdtPwd.getText().toString().trim();
                showProgress(getString(R.string.logining));
                mPresenter.onLogin(phoneNumber,pwd);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoginSuccess(final boolean isSuccess) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //发送登录状态事件
                toast(getString(R.string.login_success));
                Common.ISLOGIN=true;
                Common.username=BmobUtils.getCurrentUser().getUsername();
                Common.headUrl=BmobUtils.getCurrentUser().getHead();
                EventBus.getDefault().post(new LoginEvent(true));
                hideProgress();
                finish();
            }
        });
    }

    @Override
    public void onLoginFail(final boolean isSuccess) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.login_fail));
            }
        });
    }

    @Override
    public void onNoBusinessUserLogin() {
        toast(getString(R.string.no_arrow));
    }
}
