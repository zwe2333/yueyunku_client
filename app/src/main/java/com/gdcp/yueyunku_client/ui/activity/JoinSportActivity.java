package com.gdcp.yueyunku_client.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.event.CancelCollectEvent;
import com.gdcp.yueyunku_client.model.Collection;
import com.gdcp.yueyunku_client.model.Order;
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.utils.BmobUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Asus on 2017/5/30.
 */

public class JoinSportActivity extends BaseActivity {
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sport_img)
    ImageView mSportImg;
    @BindView(R.id.tv_business_name)
    TextView tvName;
    @BindView(R.id.sport_type)
    TextView tvType;
    @BindView(R.id.remain)
    TextView tvRemain;
    @BindView(R.id.price)
    TextView tvPrice;
    @BindView(R.id.address)
    TextView tvAddress;
    @BindView(R.id.phone)
    TextView tvPhone;
    @BindView(R.id.time)
    TextView tvTime;
    @BindView(R.id.btn_join)
    Button mBtnJoin;
    @BindView(R.id.iv_sq)
    ImageView ivSq;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    private String time = "";
    Space mSpace;


    private boolean hasCollect=false;
    private String obId=null;
    private String orderId=null;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSpace = getSportType();

        mTvToolTitle.setText(mSpace.getType());


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        time = sdf.format(date);


        //检查订单状态
        checkOrderState();
        //检查收藏状态
        checkCollectState();


        if (mSpace.getPicUrl() != null) {
            Glide.with(this).load(mSpace.getPicUrl()).into(mSportImg);
        }
        tvName.setText(mSpace.getBusiness().getUsername());



        tvType.setText("运动类型：" + mSpace.getType());

        if (mSpace.getHadBooks() == null) {
            tvRemain.setText("剩余个数：" + (mSpace.getNum()) + "个");
        } else {
            tvRemain.setText("剩余个数：" + (mSpace.getNum() - mSpace.getHadBooks()) + "个");
        }
        tvPrice.setText("价格：" + mSpace.getPrice() + "元/时");
        tvAddress.setText("地址：" + mSpace.getBusiness().getArea());
        tvPhone.setText("联系方式：" + mSpace.getBusiness().getMobilePhoneNumber());


        tvTime.setText("运动时间：" + sdf.format(date) + " 早上9:00-下午6:00");
    }

    //检查收藏状态
    private void checkCollectState() {
        BmobQuery<Collection> query=new BmobQuery<>();
        query.addWhereEqualTo("collector",BmobUtils.getCurrentUser());
        query.addWhereEqualTo("obId",mSpace.getObjectId());
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e==null){
                    if (list.size()==0){
                        hasCollect=false;
                        ivSq.setImageResource(R.mipmap.zan_on);
                    }else {
                        hasCollect=true;
                        obId=list.get(0).getObjectId();
                        ivSq.setImageResource(R.mipmap.zan);
                    }
                }
            }
        });
    }

    private void checkOrderState() {
        if (mSpace.getHadBooks() != null) {
            if (mSpace.getNum() - mSpace.getHadBooks() == 0) {
                mBtnJoin.setClickable(false);
                mBtnJoin.setText("无法加入");
                return;
            }
        }
        BmobQuery<Order> query = new BmobQuery<>();
        query.addWhereEqualTo("joiner", BmobUtils.getCurrentUser());
        query.addWhereEqualTo("business", mSpace.getBusiness());
        query.addWhereEqualTo("sport_type", mSpace.getType());
        query.addWhereEqualTo("book_time", time);//检查的是第二天的状态
        query.include("joiner");
        query.include("business");
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        mBtnJoin.setClickable(false);
                        if (list.get(0).getState_type() == 0) {
                            mBtnJoin.setText("正在审核");
                            orderId=list.get(0).getObjectId();
                            btn_cancel.setVisibility(View.VISIBLE);
                        } else if (list.get(0).getState_type() == 1) {
                            mBtnJoin.setText("已经通过");
                            btn_cancel.setVisibility(View.GONE);
                        } else if (list.get(0).getState_type() == 2) {
                            mBtnJoin.setText("未通过");
                            btn_cancel.setVisibility(View.GONE);
                        } else if (list.get(0).getState_type() == 3) {
                            mBtnJoin.setText("已经结束");
                            btn_cancel.setVisibility(View.GONE);
                        }
                    }else {
                        mBtnJoin.setText("加入运动");
                        btn_cancel.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public Space getSportType() {
        return (Space) getIntent().getSerializableExtra("sport_type");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_join_sport;
    }



    private void collectOrCancel() {
        if (!hasCollect){
            Collection collection=new Collection();
            collection.setCollector(BmobUtils.getCurrentUser());
            collection.setObId(mSpace.getObjectId());
            collection.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        toast("收藏成功");
                        hasCollect=true;
                        obId=s;
                        ivSq.setImageResource(R.mipmap.zan);
                        EventBus.getDefault().post(new CancelCollectEvent(true));
                    }
                }
            });
        }else {
            Collection collection=new Collection();
            collection.setObjectId(obId);
            collection.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        toast("取消收藏");
                        hasCollect=false;
                        EventBus.getDefault().post(new CancelCollectEvent(true));
                        checkCollectState();
                    }else {
                        toast("操作失败");
                    }
                }
            });
        }

    }


    private void joinSport() {
        Order order = new Order();
        order.setSport_type(mSpace.getType());
        order.setJoiner(BmobUtils.getCurrentUser());
        order.setBusiness(mSpace.getBusiness());
        order.setSpace(mSpace);
        order.setState_type(0);//未审核
        order.setBook_time(time);
        order.setPrice(mSpace.getPrice());
        order.setOrderUrl(mSpace.getPicUrl());
        order.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("提交成功，请等待商家审核");
                    checkOrderState();
                } else {
                    toast("提交失败");
                }
            }
        });
    }


    @OnClick({R.id.iv_sq, R.id.btn_join,R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sq:
                if (Common.ISLOGIN){
                    collectOrCancel();
                }else {
                    startActivity(LoginActivity.class,false);
                }

                break;
            case R.id.btn_join:
                if (Common.ISLOGIN){
                    joinSport();
                }else {
                    startActivity(LoginActivity.class,false);
                }
                break;
            case R.id.btn_cancel:
                deleteOrder();
                break;
        }
    }

    private void deleteOrder() {
        Order order=new Order();
        order.setObjectId(orderId);
        order.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    toast("取消加入");
                    checkOrderState();
                }else {
                    toast("操作失败");
                }
            }
        });
    }
}
