package com.gdcp.yueyunku_client.presenter.impl;

import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.YuedongquanPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.YuedongquanView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/11.
 */

public class YuedongquanPresenterImpl implements YuedongquanPresenter{
    YuedongquanView mView;
    public YuedongquanPresenterImpl(YuedongquanView view){
        mView=view;
    }

    @Override
    public void onLoadData() {
        BmobUtils.getDynamicContent(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e==null){
                    mView.onLoadDataSuccess(list);
                }else{
                    mView.onLoadDataFail();
                }
            }
        });
    }

    @Override
    public void onCheckZanIfHasDone(final Dynamic dynamic) {
        BmobQuery<User> query=new BmobQuery<>();
        Dynamic dynamic1=new Dynamic();
        dynamic1.setObjectId(dynamic.getObjectId());
        query.addWhereRelatedTo("zan",new BmobPointer(dynamic1));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        mView.onMakeZanPro(dynamic);
                        return;
                    }
                    for (User user:list){
                        if (user.getObjectId().equals(BmobUtils.getCurrentUser().getObjectId())){
                            mView.onZanHasDone();
                            return;
                        }
                    }
                    mView.onMakeZanPro(dynamic);
                }
            }
        });
    }

    @Override
    public void onMakeZan(final Dynamic dynamic) {
        if (BmobUtils.getCurrentUser()==null){
            mView.onNoLogin();
            return;
        }
        Dynamic dynamic1=new Dynamic();
        dynamic1.setObjectId(dynamic.getObjectId());
        BmobRelation relation = new BmobRelation();
        relation.add(BmobUtils.getCurrentUser());
        dynamic1.setZan(relation);
        dynamic1.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    mView.onSetZan(dynamic);
                    //setZanLength(dynamic);
                }
            }
        });
    }

    @Override
    public void onSetZanLength(final Dynamic dynamic) {
        Dynamic dynamic1=new Dynamic();
        dynamic1.increment("like");//原子计数器
        dynamic1.update(dynamic.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    mView.onRefreshZanLength();
                }
            }
        });
    }

    @Override
    public void onLoadMoreData(String lastCreateAt) {
        BmobUtils.loadMoreData(lastCreateAt, new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e==null){
                    mView.onLoadMoreDataSuccess(list);
                }else {
                    mView.onLoadMoreDataFail();
                }
            }
        });
    }

    @Override
    public void onPullToLoadData() {
        BmobUtils.getDynamicByPull(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e==null){
                    mView.onLoadDataSuccess(list);
                }else {
                    mView.onLoadDataFail();
                }
            }
        });
    }

}
