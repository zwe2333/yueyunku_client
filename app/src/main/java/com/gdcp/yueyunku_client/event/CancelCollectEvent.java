package com.gdcp.yueyunku_client.event;

/**
 * Created by Asus on 2017/6/1.
 */

public class CancelCollectEvent {
    private boolean isCancel;

    public boolean isCancel() {
        return isCancel;
    }
    public CancelCollectEvent(boolean isCancel){
        this.isCancel=isCancel;
    }
}
