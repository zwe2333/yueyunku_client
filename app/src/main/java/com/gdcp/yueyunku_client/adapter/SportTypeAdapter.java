package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Places;
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.ui.activity.JoinSportActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/29.
 */

public class SportTypeAdapter extends RecyclerView.Adapter<SportTypeAdapter.ViewHolder>{
    private Context mContext;
    private List<Space> mPlaces;

    public SportTypeAdapter(Context mContext,List<Space> mPlaces){
        this.mContext=mContext;
        this.mPlaces=mPlaces;
    }
    @Override
    public SportTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_sport_type,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SportTypeAdapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(mPlaces.get(position).getType());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, JoinSportActivity.class);
                intent.putExtra("sport_type",mPlaces.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLayout;
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mLayout= (LinearLayout) itemView.findViewById(R.id.ll_type);
            mTextView= (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
