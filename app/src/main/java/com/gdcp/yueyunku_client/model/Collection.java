package com.gdcp.yueyunku_client.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Asus on 2017/5/31.
 */

public class Collection extends BmobObject{
    private User collector;
    private Space space;
    private String obId;

    public String getObId() {
        return obId;
    }

    public void setObId(String obId) {
        this.obId = obId;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }

    public void setCollector(User collector) {
        this.collector = collector;
    }

    public User getCollector() {
        return collector;
    }
}
