package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.presenter.RegisterPresenter;
import com.gdcp.yueyunku_client.presenter.impl.RegisterPresenterImpl;
import com.gdcp.yueyunku_client.utils.CountDownTimerUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class RegisterActivity extends BaseActivity implements RegisterView{
    private final long MILLISINFUTURE=60000;
    private final long COUNTDOWNINTERVAL=1000;

    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_set_pwd)
    EditText mEdtSetPwd;
    @BindView(R.id.edt_confirm_pwd)
    EditText mEdtConfirmPwd;
    @BindView(R.id.edt_verification_code)
    EditText mEdtVerificationCode;
    @BindView(R.id.btn_acq_code)
    Button mBtnAcqCode;
    @BindView(R.id.btn_register_person)
    Button mBtnRegisterPerson;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RegisterPresenter mPresenter;

    private CountDownTimerUtils mTimerUtils;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.register));

        mTimerUtils=new CountDownTimerUtils(MILLISINFUTURE,COUNTDOWNINTERVAL,mBtnAcqCode,this);

        mPresenter=new RegisterPresenterImpl(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.btn_acq_code, R.id.btn_register_person,R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_acq_code:
                String phoneNumber=mEdtPhone.getText().toString().trim();
                mPresenter.onAcqNumber(phoneNumber);
                break;
            case R.id.btn_register_person:
                hideKeyBoard();
                showProgress(getString(R.string.wait));
                String phoneNumber2=mEdtPhone.getText().toString().trim();
                String password=mEdtSetPwd.getText().toString().trim();
                String confirmPwd=mEdtConfirmPwd.getText().toString().toString();
                String verificationCode=mEdtVerificationCode.getText().toString().trim();
                mPresenter.onRegisterAccount(phoneNumber2,password,confirmPwd,verificationCode);
                break;
            case R.id.tv_login:
                startActivity(LoginActivity.class,true);
                break;
        }
    }

    @Override
    public void onCheckNumberSuccess(boolean isStart) {
        //toast(getString(R.string.right));
    }

    @Override
    public void onCheckNumberError() {
        hideProgress();
        toast(getString(R.string.error));
    }

    @Override
    public void onSendRequestSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.sendcodesuccess));
                mTimerUtils.start();
            }
        });
    }

    @Override
    public void onPwdNullError() {
        hideProgress();
        toast(getString(R.string.pwdnull));
    }

    @Override
    public void onPwdNoSameError() {
        hideProgress();
        toast(getString(R.string.pwdnosame));
    }

    @Override
    public void onPwdLengthError() {
        hideProgress();
        toast(getString(R.string.pwdfalse));
    }

    @Override
    public void onRegisterSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.registersuccess));
            }
        });

    }

    @Override
    public void onRegisterFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                toast(getString(R.string.registerfail));
            }
        });

    }

    @Override
    public void onSendRequestFail() {
        toast(getString(R.string.send_request_fail));
    }
}
