package com.gdcp.yueyunku_client.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.utils.ThreadUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/17.
 */

public class SelectGenderActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_select_gender)
    EditText mEdtSelectGender;

    private String[] sexArry={"男","女"};

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.gender));
        mTvSend.setVisibility(View.VISIBLE);
        mTvSend.setText(getString(R.string.save));

        String gender=getIntent().getStringExtra("gender");
        mEdtSelectGender.setText(gender);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_select_gender;
    }


    @OnClick({R.id.tv_send, R.id.edt_select_gender})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                upDateGender();
                break;
            case R.id.edt_select_gender:
                showSexChooseDialog();
                break;
        }
    }

    private void upDateGender() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                String gender=mEdtSelectGender.getText().toString();
                User newUser = new User();
                newUser.setGender(gender);
                User bmobUser = User.getCurrentUser(User.class);
                newUser.update(bmobUser.getObjectId(), new UpdateListener() {
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

    private void showSexChooseDialog() {
        int choose=0;
        String gender=mEdtSelectGender.getText().toString();
        if (gender.equals("男")){
            choose=0;
        }else {
            choose=1;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(sexArry, choose, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mEdtSelectGender.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }
}
