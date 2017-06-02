package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.SaveMsgEvent;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.utils.BmobUtils;
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

public class NameActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_name)
    EditText mEdtName;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.username));
        mTvSend.setVisibility(View.VISIBLE);
        mTvSend.setText(getString(R.string.save));

        String username=getIntent().getStringExtra("username");
        mEdtName.setText(username);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_name;
    }


    @OnClick(R.id.tv_send)
    public void onClick() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                String username=mEdtName.getText().toString().trim();
                User user=new User();
                user.setUsername(username);
                User currentUser= BmobUtils.getCurrentUser();
                user.update(currentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast(getString(R.string.update_success));
                                    Common.username=BmobUtils.getCurrentUser().getUsername();
                                    EventBus.getDefault().post(new SaveMsgEvent(true));
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
