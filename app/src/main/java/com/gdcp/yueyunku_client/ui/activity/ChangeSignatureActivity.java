package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.utils.ThreadUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/17.
 */

public class ChangeSignatureActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_signature)
    EditText mEdtSignature;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.sign_name));
        mTvSend.setVisibility(View.VISIBLE);
        mTvSend.setText(getString(R.string.save));

        String signature=getIntent().getStringExtra("signature");
        if (signature.equals("未填写")){
            mEdtSignature.setText("");
        }else {
            mEdtSignature.setText(signature);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_signature;
    }


    @OnClick(R.id.tv_send)
    public void onClick() {
        String signature=mEdtSignature.getText().toString();
        updateSignature(signature);
    }

    private void updateSignature(final String signature) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                User user=new User();
                user.setSignature(signature);
                User bmobUser = User.getCurrentUser(User.class);
                user.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast(getString(R.string.update_success));
                                    finish();
                                }
                            });
                        }else {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast(getString(R.string.update_fail));
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
