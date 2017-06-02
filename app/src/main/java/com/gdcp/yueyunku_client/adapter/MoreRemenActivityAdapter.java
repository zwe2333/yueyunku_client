package com.gdcp.yueyunku_client.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.ui.activity.DetailActivity;

/**
 * Created by asus- on 2017/5/14.
 */

public class MoreRemenActivityAdapter extends RecyclerView.Adapter<MoreRemenActivityAdapter.ViewHolder>{
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                holder.itemView.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mLayout= (LinearLayout) itemView.findViewById(R.id.ll_hot);
        }
    }
}
