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

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.SportType;
import com.gdcp.yueyunku_client.ui.activity.SportTypeActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/31.
 */

public class YydSportTypeAdapter extends RecyclerView.Adapter<YydSportTypeAdapter.YydHolder>{
    private List<SportType> mTypes;
    private Context mContext;
    public YydSportTypeAdapter(List<SportType> mTypes,Context mContext){
        this.mTypes=mTypes;
        this.mContext=mContext;
    }
    @Override
    public YydSportTypeAdapter.YydHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_yyd_sport_type,parent,false);
        YydHolder holder=new YydHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(YydSportTypeAdapter.YydHolder holder, final int position) {
        holder.mImageView.setImageResource(mTypes.get(position).getUrl());
        holder.mTextView.setText(mTypes.get(position).getType());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, SportTypeActivity.class);
                intent.putExtra("sport_type_for_card",mTypes.get(position).getType());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }
    class YydHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView mImageView;
        TextView mTextView;
        public YydHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.type_url);
            mTextView= (TextView) itemView.findViewById(R.id.type_name);
            mCardView= (CardView) itemView.findViewById(R.id.card_type);
        }
    }
}
