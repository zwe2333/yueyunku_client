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
import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;
import com.gdcp.yueyunku_client.ui.activity.CommentActivity;
import com.gdcp.yueyunku_client.utils.BmobUtils;
import com.gdcp.yueyunku_client.utils.ThreadUtils;
import com.gdcp.yueyunku_client.view.YuedongquanView;
import com.gdcp.yueyunku_client.view.YueyundongView;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/11.
 */

public class YuedongquanItemAdapter extends RecyclerView.Adapter<YuedongquanItemAdapter.ViewHolder>{
    private List<Dynamic>dataList;
    private Context context;
    private YuedongquanView mView;

    public YuedongquanItemAdapter(Context context, List<Dynamic> dataList,YuedongquanView view) {
        this.context=context;
        this.dataList = dataList;
        mView=view;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_yuedongquan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String headUrl=dataList.get(position).getAuthor().getHead();
        if (headUrl!=null){
            Glide.with(holder.itemView.getContext()).load(headUrl).into(holder.cvHead);
        }else {
            holder.cvHead.setImageResource(R.mipmap.logo3);
        }
        //queryData(dataList.get(position),holder);
        holder.name.setText(dataList.get(position).getAuthor().getUsername());
        holder.time.setText(dataList.get(position).getDate().getDate());
        holder.content.setText(dataList.get(position).getContent());
        holder.tv_comment.setText(dataList.get(position).getComment()+"");
        holder.gridView.setImagesData(dataList.get(position).getImages());
        holder.img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), CommentActivity.class);
                intent.putExtra("Dynamic",dataList.get(position));
                intent.putExtra("visible","nogone");
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.tv_zan.setText(dataList.get(position).getLike()+"");
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.onCheckZan(dataList.get(position));
            }
        });
    }

    private void queryData(Dynamic dynamic, final ViewHolder holder) {
        BmobQuery<User> query=new BmobQuery<>();
        Dynamic dynamic1=new Dynamic();
        dynamic1.setObjectId(dynamic.getObjectId());
        query.addWhereRelatedTo("zan", new BmobPointer(dynamic1));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                for (User user:list){
                    if (user.getObjectId().equals(BmobUtils.getCurrentUser().getObjectId())){
                        holder.iv_zan.setImageResource(R.mipmap.zan);
                        return;
                    }
                }
                holder.iv_zan.setImageResource(R.mipmap.zan_on);
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
        public ViewHolder(final View itemView) {
            super(itemView);
            myNineGridImageViewAdapter=new MyNineGridImageViewAdapter();
            name= (TextView) itemView.findViewById(R.id.tv_user_name);
            content= (TextView) itemView.findViewById(R.id.tv_content);
            time= (TextView) itemView.findViewById(R.id.cv_time_fabiao);
            tv_zan= (TextView) itemView.findViewById(R.id.tv_zan);
            tv_comment= (TextView) itemView.findViewById(R.id.tv_comment);
            tv_del= (TextView) itemView.findViewById(R.id.tv_del);
            tv_del.setVisibility(View.GONE);
            iv_zan= (ImageView) itemView.findViewById(R.id.iv_zan);
            cvHead= (ImageView) itemView.findViewById(R.id.cv_head);
            img_comment= (ImageView) itemView.findViewById(R.id.img_comment);

            gridView= (NineGridImageView) itemView.findViewById(R.id.gv_pic_fabiao);
            gridView.setAdapter(myNineGridImageViewAdapter);
        }
    }
}
