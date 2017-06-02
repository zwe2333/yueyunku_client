package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/25.
 */

public class ResetPwdActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_set_pwd)
    EditText mEdtSetPwd;
    @BindView(R.id.edt_verification_code)
    EditText mEdtVerificationCode;
    @BindView(R.id.btn_reset_pwd)
    Button mResetPwd;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.btn_reset_pwd));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_reset_pwd;
    }


    @OnClick(R.id.btn_reset_pwd)
    public void onClick() {
        String pwd=mEdtSetPwd.getText().toString();
        String code=mEdtVerificationCode.getText().toString();
        reset(pwd,code);
    }

    private void reset(String pwd, String code) {
        BmobUser.resetPasswordBySMSCode(code,pwd, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    toast(getString(R.string.reset_pwd_success));
                    finish();
                }else{
                    toast(getString(R.string.reset_pwd_fail));
                }
            }
        });
    }
}
