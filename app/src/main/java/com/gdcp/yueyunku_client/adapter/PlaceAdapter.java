package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Place;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.ui.activity.PlaceActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/29.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{
    private List<User> mPlaces;
    private Context mContext;
    public PlaceAdapter(List<User> mPlaces, Context mContext){
        this.mPlaces=mPlaces;
        this.mContext=mContext;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_yueyundong,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, final int position) {
        if (mPlaces.get(position).getBackgroundUrl()!=null){
            Glide.with(mContext).load(mPlaces.get(position).getBackgroundUrl()).into(holder.mImageView);
        }
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, PlaceActivity.class);
                intent.putExtra("place",mPlaces.get(position).getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPlaces.size()>6){
            return 6;
        }else {
            return mPlaces.size();
        }
        //只弄6个，多余的则要点击更多获取

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
