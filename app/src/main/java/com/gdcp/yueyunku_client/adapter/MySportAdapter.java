package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Order;
import com.gdcp.yueyunku_client.model.Space;

import java.util.List;

/**
 * Created by Asus on 2017/5/30.
 */

public class MySportAdapter extends RecyclerView.Adapter<MySportAdapter.MySportHolder>{
    private Context mContext;
    private List<Order> mOrders;

    public MySportAdapter(Context mContext,List<Order> mOrders){
        this.mContext=mContext;
        this.mOrders=mOrders;
    }

    @Override
    public MySportAdapter.MySportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_my_sport,parent,false);
        MySportHolder holder=new MySportHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MySportAdapter.MySportHolder holder, int position) {
        Order order=mOrders.get(position);
        int stete=order.getState_type();
        if (order.getOrderUrl()!=null){
            Glide.with(mContext).load(order.getOrderUrl()).into(holder.mView);
        }
        holder.mBusiness.setText("商家："+order.getBusiness().getUsername()+"("+order.getSport_type()+")");
        holder.mPhone.setText("联系方式："+order.getBusiness().getMobilePhoneNumber());
        holder.mTime.setText("时间："+order.getBook_time());
        holder.mPrice.setText("价格："+order.getPrice()+"元/时");
        switch (stete){
            case 0:
                holder.mState.setText("正在审核");
                break;
            case 1:
                holder.mState.setText("已经通过");
                break;
            case 2:
                holder.mState.setText("未通过");
                break;
            case 3:
                holder.mState.setText("已经结束");
                break;
        }
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }
    class MySportHolder extends RecyclerView.ViewHolder{
        ImageView mView;
        TextView mBusiness;
        TextView mPhone;
        TextView mTime;
        TextView mPrice;
        TextView mState;
        CardView mCardView;
        public MySportHolder(View itemView) {
            super(itemView);
            mView= (ImageView) itemView.findViewById(R.id.iv_sport_url);
            mBusiness= (TextView) itemView.findViewById(R.id.tv_business_name);
            mPhone= (TextView) itemView.findViewById(R.id.tv_phone);
            mTime= (TextView) itemView.findViewById(R.id.tv_time);
            mPrice= (TextView) itemView.findViewById(R.id.tv_price);
            mState= (TextView) itemView.findViewById(R.id.tv_state);
            mCardView= (CardView) itemView.findViewById(R.id.sport_card);

        }
    }
}
