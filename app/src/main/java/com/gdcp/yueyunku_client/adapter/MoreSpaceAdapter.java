package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.ui.activity.PlaceActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/30.
 */

public class MoreSpaceAdapter extends RecyclerView.Adapter<MoreSpaceAdapter.MoreSpaceHolder>{
    private Context mContext;
    private List<User> mUsers;
    public MoreSpaceAdapter(Context mContext,List<User> mUsers){
        this.mContext=mContext;
        this.mUsers=mUsers;
    }
    @Override
    public MoreSpaceAdapter.MoreSpaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_more_space,parent,false);
        MoreSpaceHolder holder=new MoreSpaceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MoreSpaceAdapter.MoreSpaceHolder holder, final int position) {
        User user=mUsers.get(position);
        if (user.getBackgroundUrl()!=null){
            Glide.with(mContext).load(user.getBackgroundUrl()).into(holder.mImageView);
        }
        if (user.getVendorName()!=null){
            holder.tvBusinessName.setText("商家："+user.getVendorName());
        }else {
            holder.tvBusinessName.setText("商家：（名称暂未设置）");
        }

        holder.tvAddress.setText("地址："+user.getArea());
        holder.tvPhone.setText("联系方式"+user.getMobilePhoneNumber());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, PlaceActivity.class);
                intent.putExtra("place",mUsers.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
    class MoreSpaceHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView tvBusinessName;
        TextView tvAddress;
        TextView tvPhone;
        CardView mCardView;
        public MoreSpaceHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.space_url);
            tvBusinessName= (TextView) itemView.findViewById(R.id.tv_business_name);
            tvAddress= (TextView) itemView.findViewById(R.id.tv_address);
            tvPhone= (TextView) itemView.findViewById(R.id.tv_phone);
            mCardView= (CardView) itemView.findViewById(R.id.card_space);
        }
    }
}
