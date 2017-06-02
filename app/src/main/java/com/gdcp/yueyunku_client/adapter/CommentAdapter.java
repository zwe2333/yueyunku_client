package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Comment;
import com.gdcp.yueyunku_client.model.Dynamic;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus- on 2017/5/14.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mComments;
    private Context mContext;
    public CommentAdapter(List<Comment> mComments,Context mContext){
        this.mComments=mComments;
        this.mContext=mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment=mComments.get(position);
        holder.tv_content.setText(comment.getContent());
        holder.tv_username.setText(comment.getUser().getUsername());
        Glide.with(mContext).load(comment.getUser().getHead()).into(holder.img_head);
        holder.tv_time.setText(comment.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_username;
        TextView tv_time;
        TextView tv_content;
        CircleImageView img_head;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_username= (TextView) itemView.findViewById(R.id.tv_name_commenter);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time_comment);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content_comment);
            img_head= (CircleImageView) itemView.findViewById(R.id.iv_head_commenter);
        }
    }
}
