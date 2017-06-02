package com.gdcp.yueyunku_client.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Asus on 2017/5/18.
 */

public class Comment extends BmobObject{
    private String content;
    private User user;
    private Dynamic dynamic;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
