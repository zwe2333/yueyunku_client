package com.gdcp.yueyunku_client.presenter;

import com.gdcp.yueyunku_client.model.Comment;
import com.gdcp.yueyunku_client.model.Dynamic;

import java.util.List;

/**
 * Created by Asus on 2017/5/18.
 */

public interface CommentPresenter {
    void onCheckComment(String content, Dynamic dynamic);

    List<Comment> getList();

    void onQueryComments(Dynamic dynamic);

    void onUpdataCommentLength(Dynamic dynamic);
}
