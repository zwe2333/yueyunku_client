package com.gdcp.yueyunku_client.event;

/**
 * Created by Asus on 2017/5/17.
 */

public class SaveMsgEvent {
    boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public SaveMsgEvent(boolean isSuccess){
        this.isSuccess=isSuccess;
    }
}
