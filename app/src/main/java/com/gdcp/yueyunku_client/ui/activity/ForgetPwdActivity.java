package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Asus on 2017/5/25.
 */

public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    private boolean isExits=false;

    @Override
    protected void init() {
        mTvToolTitle.setText(getString(R.string.forgetpwd));
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_forget_pwd;
    }


    @OnClick(R.id.btn_next)
    public void onClick() {
        String phoneNumber=mEdtPhone.getText().toString().trim();
        if (StringUtils.isMobileNo(phoneNumber)){
            checkTheAccount(phoneNumber);
        }
    }

    private void checkTheAccount(final String phoneNumber) {
        BmobQuery<BmobUser> query=new BmobQuery<>();
        query.addWhereEqualTo("mobilePhoneNumber", phoneNumber);
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if (e==null){
                    if (list.size()!=0){
                        requestSmsAndReset(phoneNumber);
                    }else {
                        toast(getString(R.string.account_no_exits));
                    }

                }
            }
        });
    }

    private void requestSmsAndReset(String phoneNumber) {
        BmobUtils.sendCode(phoneNumber, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null){
                    toast(getString(R.string.send_code_success));
                    startActivity(ResetPwdActivity.class,true);
                }else {
                    toast(getString(R.string.send_code_fail));
                }
            }
        });
    }
}
