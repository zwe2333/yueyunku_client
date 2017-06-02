package com.gdcp.yueyunku_client.model;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Asus on 2017/5/15.
 */

public class Dynamic extends BmobObject implements Serializable{
    private String content;//内容
    private User author;//作者，这里体现的是一对一的关系，该帖子属于某个用户
    private BmobDate date;//储存日期date=new BmobDate(new Date);
    private List<String> images;//储存图片（9张）
    private BmobGeoPoint point;//储存坐标轴
    private Integer comment;
    private Integer like;
    private BmobRelation zan;

    public void setZan(BmobRelation zan) {
        this.zan = zan;
    }

    public BmobRelation getZan() {
        return zan;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public BmobGeoPoint getPoint() {
        return point;
    }

    public void setPoint(BmobGeoPoint point) {
        this.point = point;
    }
}
