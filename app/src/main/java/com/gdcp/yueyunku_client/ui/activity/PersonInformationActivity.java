package com.gdcp.yueyunku_client.ui.activity;

/**
 * Created by Asus on 2017/5/16.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.SaveMsgEvent;
import com.gdcp.yueyunku_client.presenter.PersonInformationPresenter;
import com.gdcp.yueyunku_client.presenter.impl.PersonInformationPresenterImpl;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.PersonInformationView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInformationActivity extends BaseActivity implements PersonInformationView{

    private String imgUrl=null;

    private static final int IMAGE_CODE = 1001;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.look_head)
    CircleImageView mLookHead;
    @BindView(R.id.rl_change_head)
    RelativeLayout mRlChangeHead;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.rl_change_name)
    RelativeLayout mRlChangeName;
    @BindView(R.id.tv_gender)
    TextView mTvGender;
    @BindView(R.id.rl_change_gender)
    RelativeLayout mRlChangeGender;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.rl_change_area)
    RelativeLayout mRlChangeArea;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    @BindView(R.id.rl_change_signature)
    RelativeLayout mRlChangeSignature;

    private PersonInformationPresenter mPresenter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.person_msg));


        mPresenter=new PersonInformationPresenterImpl(this);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_person_information;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onLoadPersonMsg();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void putData(Class activiy, String name, String data){
        Intent intent=new Intent(this,activiy);
        intent.putExtra(name,data);
        startActivity(intent);
    };

    @OnClick({R.id.look_head, R.id.rl_change_head, R.id.rl_change_name, R.id.rl_change_gender, R.id.rl_change_area, R.id.rl_change_signature})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look_head:
                putData(HeadActivity.class,"head_url",BmobUtils.getCurrentUser().getHead());
                break;
            case R.id.rl_change_head:
                if (ContextCompat.checkSelfPermission(PersonInformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonInformationActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    selectImage();
                }
                break;
            case R.id.rl_change_name:
                putData(NameActivity.class,"username",mTvUsername.getText().toString());
                break;
            case R.id.rl_change_gender:
                putData(SelectGenderActivity.class,"gender",mTvGender.getText().toString());
                break;
            case R.id.rl_change_area:
                putData(ShowAreaActivity.class,"area",mTvArea.getText().toString());
                break;
            case R.id.rl_change_signature:
                putData(ChangeSignatureActivity.class,"signature",mTvSignature.getText().toString());
                break;
        }
    }
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==IMAGE_CODE) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor cursor = managedQuery(uri, proj, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            imgUrl = cursor.getString(column_index);

            //上传头像
            mPresenter.onUploadHead(imgUrl);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadHeadSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.upload_head_success));
                Glide.with(PersonInformationActivity.this).load(BmobUtils.getCurrentUser().getHead()).into(mLookHead);
                Common.headUrl=BmobUtils.getCurrentUser().getHead();
                Common.isStartForYdc=true;
                EventBus.getDefault().post(new SaveMsgEvent(true));
            }
        });

    }

    @Override
    public void onUploadHeadFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.upload_head_fail));
            }
        });
    }

    @Override
    public void onSetMsg(String username, String head, String gender,String signature,String area) {
        mTvUsername.setText(username);
        if (head==null){
            mLookHead.setImageResource(R.mipmap.logo2);
        }else {
            Glide.with(this).load(head).into(mLookHead);
        }
        if (gender==null){
            mTvGender.setText(getString(R.string.no_write));
        }else {
            mTvGender.setText(gender);
        }
        if (signature==null){
            mTvSignature.setText(R.string.no_write);
        }else {
            mTvSignature.setText(signature);
        }
        if (area==null){
            mTvArea.setText(R.string.no_write);
        }else {
            mTvArea.setText(area);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    selectImage();
                }
                break;
        }
    }
}
