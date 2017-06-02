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
import com.gdcp.yueyunku_client.model.Space;
import com.gdcp.yueyunku_client.ui.activity.JoinSportActivity;

import java.util.List;

/**
 * Created by Asus on 2017/5/31.
 */

public class SportSpaceAdapter extends RecyclerView.Adapter<SportSpaceAdapter.SportSpaceHolder>{
    private Context mContext;
    private List<Space> mSpaces;

    public SportSpaceAdapter(Context mContext,List<Space> mSpaces){
        this.mContext=mContext;
        this.mSpaces=mSpaces;
    }

    @Override
    public SportSpaceAdapter.SportSpaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_sport_space_type,parent,false);
        SportSpaceHolder holder=new SportSpaceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SportSpaceAdapter.SportSpaceHolder holder, final int position) {
        Space space=mSpaces.get(position);
        if (space.getPicUrl()!=null){
            Glide.with(mContext).load(space.getPicUrl()).into(holder.mImageView);
        }
        holder.mTextView.setText(space.getBusiness().getUsername());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, JoinSportActivity.class);
                intent.putExtra("sport_type",mSpaces.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpaces.size();
    }
    class SportSpaceHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;
        public SportSpaceHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_sport_space);
            mTextView= (TextView) itemView.findViewById(R.id.tv_business_name);
            mCardView= (CardView) itemView.findViewById(R.id.card_space_type);
        }
    }
}
