package com.gdcp.yueyunku_client.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Asus on 2017/5/11.
 */

public class User extends BmobUser{
    private boolean isBoy;
    private String gender;
    private Integer age;
    private Integer type;
    private String head;
    private String signature;
    private String area;
    private String vendorName;
    private String backgroundUrl;
    private String placeIntro;

    public void setPlaceIntro(String placeIntro) {
        this.placeIntro = placeIntro;
    }

    public String getPlaceIntro() {
        return placeIntro;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public boolean isBoy() {
        return isBoy;
    }

    public void setBoy(boolean boy) {
        isBoy = boy;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }
}
