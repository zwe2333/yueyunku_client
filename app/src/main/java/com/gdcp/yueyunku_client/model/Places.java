package com.gdcp.yueyunku_client.model;

/**
 * Created by asus- on 2017/5/28.
 */

public class Places{
    private String type;
    private Integer price;
    private Integer num;

    public Places(String type, Integer price, Integer num) {
        this.type = type;
        this.price = price;
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


}
