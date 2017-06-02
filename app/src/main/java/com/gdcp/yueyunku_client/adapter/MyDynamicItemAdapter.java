package com.gdcp.yueyunku_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_client.R;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.ui.activity.CommentActivity;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.view.MyDynamicView;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.List;

/**
 * Created by Asus on 2017/5/24.
 */

public class MyDynamicItemAdapter extends RecyclerView.Adapter<MyDynamicItemAdapter.ViewHolder>{
    private List<Dynamic> dataList;
    private Context context;
    private MyDynamicView mView;

    public MyDynamicItemAdapter(Context context, List<Dynamic> dataList,MyDynamicView mView){
        this.context=context;
        this.dataList=dataList;
        this.mView=mView;
    }
    @Override
    public MyDynamicItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_yuedongquan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyDynamicItemAdapter.ViewHolder holder, final int position) {
        String headUrl=BmobUtils.getCurrentUser().getHead();
        if (headUrl!=null){
            Glide.with(holder.itemView.getContext()).load(headUrl).into(holder.cvHead);
        }else {
            holder.cvHead.setImageResource(R.mipmap.logo3);
        }
        holder.name.setText(BmobUtils.getCurrentUser().getUsername());
        holder.time.setText(dataList.get(position).getDate().getDate());
        holder.content.setText(dataList.get(position).getContent());
        holder.tv_comment.setText(dataList.get(position).getComment()+"");
        holder.gridView.setImagesData(dataList.get(position).getImages());
        holder.img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), CommentActivity.class);
                intent.putExtra("Dynamic",dataList.get(position));
                intent.putExtra("visible","gone");
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.tv_zan.setText(dataList.get(position).getLike()+"");
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.onDelDynamic(dataList.get(position));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private MyNineGridImageViewAdapter myNineGridImageViewAdapter;
        TextView name;
        TextView content;
        TextView time;
        TextView tv_zan;
        TextView tv_comment;
        TextView tv_del;
        ImageView iv_zan;
        ImageView cvHead;
        ImageView img_comment;
        NineGridImageView gridView;
        public ViewHolder(View itemView) {
            super(itemView);
            myNineGridImageViewAdapter=new MyNineGridImageViewAdapter();
            name= (TextView) itemView.findViewById(R.id.tv_user_name);
            content= (TextView) itemView.findViewById(R.id.tv_content);
            time= (TextView) itemView.findViewById(R.id.cv_time_fabiao);
            tv_zan= (TextView) itemView.findViewById(R.id.tv_zan);
            tv_comment= (TextView) itemView.findViewById(R.id.tv_comment);
            tv_del= (TextView) itemView.findViewById(R.id.tv_del);
            iv_zan= (ImageView) itemView.findViewById(R.id.iv_zan);
            cvHead= (ImageView) itemView.findViewById(R.id.cv_head);
            img_comment= (ImageView) itemView.findViewById(R.id.img_comment);

            gridView= (NineGridImageView) itemView.findViewById(R.id.gv_pic_fabiao);
            gridView.setAdapter(myNineGridImageViewAdapter);
        }
    }
}
