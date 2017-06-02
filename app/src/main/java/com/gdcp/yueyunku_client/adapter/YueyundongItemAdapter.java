package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Place;

import java.util.List;

/**
 * Created by asus- on 2017/5/11.
 */

public class YueyundongItemAdapter extends RecyclerView.Adapter<YueyundongItemAdapter.ViewHolder>{
    private List<Integer>dataList;
    private List<Place> mDataList;
    private Context context;

    public YueyundongItemAdapter(Context context, List<Integer> dataList) {
        this.context=context;
        this.dataList = dataList;
    }
    public YueyundongItemAdapter(Context context, List<Place> mDataList,String tag) {
        this.context=context;
        this.mDataList = mDataList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_yueyundong,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position)).into(holder.img);
        //Glide.with(context).load(mDataList.get(position).getPlacePicUrl()).into(holder.img);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
