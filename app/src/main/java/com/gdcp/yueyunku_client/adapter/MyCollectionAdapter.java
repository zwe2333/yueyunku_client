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
import com.gdcp.yueyunku_client.model.Collection;
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.ui.activity.JoinSportActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/31.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.MyCollectionHolder>{
    private Context mContext;
    private List<Space> mCollections;
    public MyCollectionAdapter(Context mContext,List<Space> mCollections){
        this.mContext=mContext;
        this.mCollections=mCollections;
    }
    @Override
    public MyCollectionAdapter.MyCollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_my_colletion,parent,false);
        MyCollectionHolder holder=new MyCollectionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCollectionAdapter.MyCollectionHolder holder, final int position) {
        final Space collection=mCollections.get(position);
        if (collection.getPicUrl()!=null){
            Glide.with(mContext).load(collection.getPicUrl()).into(holder.mImageView);
        }
        holder.tv_type.setText(collection.getType());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, JoinSportActivity.class);
                intent.putExtra("sport_type",collection);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCollections.size();
    }
    class MyCollectionHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView tv_type;
        CardView mCardView;
        public MyCollectionHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_collection);
            tv_type= (TextView) itemView.findViewById(R.id.tv_sport_type);
            mCardView= (CardView) itemView.findViewById(R.id.card_collection);
        }
    }

}
