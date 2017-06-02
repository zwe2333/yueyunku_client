package com.gdcp.yueyunku_client.presenter.impl;

import android.text.TextUtils;

import com.gdcp.yueyunku_client.event.PostSuccessEvent;
import com.gdcp.yueyunku_client.model.Comment;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.CommentPresenter;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.CommentView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/18.
 */

public class CommentPresenterImpl implements CommentPresenter{
    private CommentView mView;
    private List<Comment> mComments;
    public CommentPresenterImpl(CommentView view){
        mView=view;
        mComments=new ArrayList<>();
    }

    @Override
    public void onCheckComment(final String content, final Dynamic dynamic) {
        if (TextUtils.isEmpty(content)){
            mView.onContentNullError();
            return;
        }
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                User user=BmobUtils.getCurrentUser();
                Dynamic dynamic1=new Dynamic();
                dynamic1.setObjectId(dynamic.getObjectId());
                Comment comment=new Comment();
                comment.setContent(content);
                comment.setDynamic(dynamic);
                comment.setUser(user);
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            mView.onSendCommentSuccess();
                        }else {
                            mView.onSendCommentFail();
                        }
                    }
                });
            }
        });

    }

    @Override
    public List<Comment> getList() {
        return mComments;
    }

    @Override
    public void onQueryComments(final Dynamic dynamic) {
        mComments.clear();
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                BmobUtils.getCommentContent(dynamic,new FindListener<Comment>() {
                    @Override
                    public void done(List<Comment> list, BmobException e) {
                        if (e==null){
                            mComments.addAll(list);
                            mView.onGetCommentSuccess(list.size());
                        }else {
                            mView.onGetCommentFail();
                        }
                    }
                });

            }
        });
    }


    //刷新时comment次数+1
    @Override
    public void onUpdataCommentLength(final Dynamic dynamic) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                Dynamic dynamic1=new Dynamic();
                dynamic1.increment("comment");
                dynamic1.update(dynamic.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            mView.onUpdataCommentLengthSuccess();
                        }else {
                            mView.onUpdataCommentLengthFail();
                        }
                    }
                });
            }
        });

    }
}
