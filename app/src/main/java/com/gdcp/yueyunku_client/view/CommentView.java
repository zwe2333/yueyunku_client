package com.gdcp.yueyunku_client.view;

import com.gdcp.yueyunku_client.model.Comment;

import java.util.List;

/**
 * Created by Asus on 2017/5/18.
 */

public interface CommentView {
    void onContentNullError();

    void onSendCommentSuccess();

    void onSendCommentFail();

    void onGetCommentSuccess(int size);

    void onGetCommentFail();

    void onUpdataCommentLengthSuccess();

    void onUpdataCommentLengthFail();
}
