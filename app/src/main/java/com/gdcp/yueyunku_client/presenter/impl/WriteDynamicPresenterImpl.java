package com.gdcp.yueyunku_client.presenter.impl;

import android.text.TextUtils;

import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.presenter.WriteDynamicPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.WriteDynamicView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Asus on 2017/5/14.
 */

public class WriteDynamicPresenterImpl implements WriteDynamicPresenter{
    WriteDynamicView mView;
    public WriteDynamicPresenterImpl(WriteDynamicView view){
        mView=view;
    }

    @Override
    public void onUploadDynamic(final String content, final ArrayList<String> photos) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(content) && photos.size()==0){
                    mView.onContentNullError();
                    return;
                }
                if (photos.size()==0){
                    final Dynamic dynamic=new Dynamic();
                    dynamic.setContent(content);
                    dynamic.setDate(new BmobDate(new Date()));
                    dynamic.setLike(0);
                    dynamic.setComment(0);
                    dynamic.setAuthor(BmobUtils.getCurrentUser());
                    dynamic.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                mView.onUploadSuccess();
                            }else {
                                mView.onUploadFail();
                            }
                        }
                    });
                }else {
                    String[] filePaths = new String[photos.size()];
                    for (int i=0;i<photos.size();i++){
                        filePaths[i]=photos.get(i);
                    }
                    BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> urls) {
                            if (list.size()==photos.size()){
                                //如果数量相等，则代表文件全部上传完成，此时才可以真正上传动态到服务器
                                final Dynamic dynamic=new Dynamic();
                                dynamic.setContent(content);
                                dynamic.setDate(new BmobDate(new Date()));
                                dynamic.setLike(0);
                                dynamic.setComment(0);
                                dynamic.setAuthor(BmobUtils.getCurrentUser());
                                dynamic.setImages(urls);
                                dynamic.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e==null){
                                            mView.onUploadSuccess();
                                        }else {
                                            mView.onUploadFail();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {

                        }

                        @Override
                        public void onError(int i, String s) {
                            //("错误码"+statuscode +",错误描述："+errormsg);
                        }
                    });
                }
            }
        });

    }
}
