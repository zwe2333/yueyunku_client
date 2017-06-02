package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.PersonInformationPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.PersonInformationView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Asus on 2017/5/17.
 */

public class PersonInformationPresenterImpl implements PersonInformationPresenter{
    private PersonInformationView mView;
    public PersonInformationPresenterImpl(PersonInformationView view){
        mView=view;
    }

    @Override
    public void onUploadHead(final String imgUrl) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                if (imgUrl!=null){
                    //先上传文件
                    final BmobFile file=new BmobFile(new File(imgUrl));
                    file.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                User newUser = new User();
                                newUser.setHead(file.getFileUrl());
                                User bmobUser = User.getCurrentUser(User.class);
                                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            mView.onUploadHeadSuccess();
                                        }else{
                                            mView.onUploadHeadFail();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onLoadPersonMsg() {
        String username=BmobUtils.getCurrentUser().getUsername();
        String head=BmobUtils.getCurrentUser().getHead();
        String gender=BmobUtils.getCurrentUser().getGender();
        String signature=BmobUtils.getCurrentUser().getSignature();
        String area=BmobUtils.getCurrentUser().getArea();
        mView.onSetMsg(username,head,gender,signature,area);
    }
}
