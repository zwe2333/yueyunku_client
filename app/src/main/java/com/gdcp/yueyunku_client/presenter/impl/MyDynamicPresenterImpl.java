package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.MyDynamicPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.view.MyDynamicView;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Asus on 2017/5/24.
 */

public class MyDynamicPresenterImpl implements MyDynamicPresenter{
    private MyDynamicView mView;
    public MyDynamicPresenterImpl(MyDynamicView view){
        mView=view;
    }

    @Override
    public void onLoadMyDynamic() {
        //获取背景图
        final String backgroundUrl=BmobUtils.getCurrentUser().getBackgroundUrl();
        BmobUtils.getCurrentUserDynamic(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e==null){
                    mView.onSetConfig(backgroundUrl,list);
                }
            }
        });


    }

    @Override
    public void onUploadHead(String imgUrl) {
        final BmobFile file=new BmobFile(new File(imgUrl));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    User newUser = new User();
                    newUser.setBackgroundUrl(file.getFileUrl());
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

    @Override
    public void onDelDynamic(Dynamic dynamic) {
        Dynamic dynamic1=new Dynamic();
        dynamic1.setObjectId(dynamic.getObjectId());
        dynamic1.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    mView.onDelSuccess();
                }else {
                    mView.onDelFail();
                }
            }
        });
    }

    @Override
    public void onLoadMoreDynamic(String lastCreateAt) {
        BmobUtils.loadMoreDataForMyDynamic(lastCreateAt, new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e==null){
                    mView.onLoadMoreDataSuccess(list);
                }
            }
        });
    }
}
