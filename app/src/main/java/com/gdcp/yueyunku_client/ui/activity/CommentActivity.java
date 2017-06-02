package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.CommentAdapter;
import com.gdcp.yueyunku_client.model.Comment;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.presenter.CommentPresenter;
import com.gdcp.yueyunku_client.presenter.impl.CommentPresenterImpl;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.CommentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity implements CommentView {


    @BindView(R.id.tv_num_comment)
    TextView tvNumComment;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    private CommentAdapter commentAdapter;
    @BindView(R.id.comment_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collasing)
    CollapsingToolbarLayout mLayout;
    @BindView(R.id.edt_send_comment)
    EditText mEdtSendComment;
    @BindView(R.id.btn_send_comment)
    Button mBtnSendComment;
    @BindView(R.id.ll_comment)
    LinearLayout mComment;


    private CommentPresenter mPresenter;
    private Dynamic mDynamic;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPresenter = new CommentPresenterImpl(this);
        commentAdapter = new CommentAdapter(mPresenter.getList(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setAdapter(commentAdapter);

        mDynamic= (Dynamic) getIntent().getSerializableExtra("Dynamic");
        String visible=getIntent().getStringExtra("visible");
        if (visible.equals("gone")){
            mComment.setVisibility(View.GONE);
        }

        mLayout.setTitle(mDynamic.getAuthor().getUsername());

        mPresenter.onQueryComments(mDynamic);
    }


    @OnClick(R.id.btn_send_comment)
    public void onClick() {
        String content=mEdtSendComment.getText().toString().trim();
        mPresenter.onCheckComment(content,mDynamic);
    }

    @Override
    public void onContentNullError() {
        toast(getString(R.string.content_null));
    }

    @Override
    public void onSendCommentSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdtSendComment.setText("");
                hideKeyBoard();
                //toast(getString(R.string.post_success));
                mPresenter.onUpdataCommentLength(mDynamic);
                mPresenter.onQueryComments(mDynamic);
            }
        });
    }

    @Override
    public void onSendCommentFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdtSendComment.setText("");
                hideKeyBoard();
                toast(getString(R.string.post_fail));
            }
        });
    }

    @Override
    public void onGetCommentSuccess(final int size) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNumComment.setText(size+"");
                commentAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetCommentFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.get_comment_fail));
            }
        });
    }

    @Override
    public void onUpdataCommentLengthSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //toast(getString(R.string.updata_comments_success));
            }
        });
    }

    @Override
    public void onUpdataCommentLengthFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.updata_comments_fail));
            }
        });
    }
}
