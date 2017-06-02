package com.gdcp.yueyunku_client.model;

/**
 * Created by Asus on 2017/5/31.
 */

public class SportType {
    private int url;
    private String type;

    public SportType(int url,String type){
        this.url=url;
        this.type=type;
    }

    public int getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}

