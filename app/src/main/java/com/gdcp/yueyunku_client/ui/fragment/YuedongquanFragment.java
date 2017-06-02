package com.gdcp.yueyunku_client.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.adapter.YuedongquanItemAdapter;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.PostSuccessEvent;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.presenter.YuedongquanPresenter;
import com.gdcp.yueyunku_client.presenter.impl.YuedongquanPresenterImpl;
import com.gdcp.yueyunku_client.ui.activity.LoginActivity;
import com.gdcp.yueyunku_client.ui.activity.WriteDynamicActivity;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.YuedongquanView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class YuedongquanFragment extends BaseFragment implements YuedongquanView {

    @BindView(R.id.float_btn)
    FloatingActionButton mFloatBtn;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.rv_yueyunquan)
    XRecyclerView rvYueyunquan;

    private List<Dynamic> dataList=new ArrayList<>();
    private YuedongquanItemAdapter yueYunQuanAdapter;
    private YuedongquanPresenter mPresenter;
    private String lastCreateAt=null;
    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        mPresenter = new YuedongquanPresenterImpl(this);
        mTvToolTitle.setText(getString(R.string.yue_dong_quan));

        yueYunQuanAdapter = new YuedongquanItemAdapter(getActivity(), dataList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvYueyunquan.setLayoutManager(linearLayoutManager);
        rvYueyunquan.setLoadingListener(mListener);
        rvYueyunquan.setAdapter(yueYunQuanAdapter);

        mFloatBtn.attachToRecyclerView(rvYueyunquan);

        if (Common.isStartForYdc){
            mPresenter.onLoadData();
            Common.isStartForYdc=false;
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_yuedongquan;
    }


    @OnClick({R.id.float_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_btn:
                if (Common.ISLOGIN){
                    startActivity(WriteDynamicActivity.class,false);
                }else {
                    startActivity(LoginActivity.class,false);
                }
                break;
        }


    }

    @Subscribe
    public void onRefreshData(PostSuccessEvent event) throws InterruptedException {
        if (event.isOk()==true){
            mPresenter.onPullToLoadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    XRecyclerView.LoadingListener mListener=new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            mPresenter.onPullToLoadData();
        }

        @Override
        public void onLoadMore() {
            mPresenter.onLoadMoreData(lastCreateAt);
        }
    };

    @Override
    public void onLoadDataSuccess(final List<Dynamic> list) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataList.clear();
                lastCreateAt=list.get(list.size()-1).getCreatedAt();
                dataList.addAll(list);
                rvYueyunquan.refreshComplete();
                yueYunQuanAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onLoadDataFail() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvYueyunquan.refreshComplete();
                toast(getString(R.string.load_fail));
            }
        });
    }

    @Override
    public void onCheckZan(Dynamic dynamic) {
        mPresenter.onCheckZanIfHasDone(dynamic);
    }

    @Override
    public void onZanHasDone() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.has_zan));
            }
        });
    }

    @Override
    public void onMakeZanPro(final Dynamic dynamic) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.onMakeZan(dynamic);
            }
        });

    }


    @Override
    public void onRefreshZanLength() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.onPullToLoadData();
                toast("点赞成功");
            }
        });
    }

    @Override
    public void onSetZan(final Dynamic dynamic) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.onSetZanLength(dynamic);
            }
        });
    }

    @Override
    public void onLoadMoreDataSuccess(List<Dynamic> list) {
        if (list.size()==0){
            toast(getString(R.string.no_more_data));
            rvYueyunquan.loadMoreComplete();
            return;
        }
        dataList.addAll(list);
        lastCreateAt=list.get(list.size()-1).getCreatedAt();
        rvYueyunquan.loadMoreComplete();
        yueYunQuanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoLogin() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(getString(R.string.no_login));
            }
        });
    }

    @Override
    public void onLoadMoreDataFail() {
        rvYueyunquan.loadMoreComplete();
        toast(getString(R.string.load_fail));
    }
}
