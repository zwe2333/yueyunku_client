package com.gdcp.yueyunku_client.event;

/**
 * Created by Asus on 2017/5/15.
 */

public class PostSuccessEvent {
    boolean isOk;

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public boolean isOk() {
        return isOk;
    }
    public PostSuccessEvent(boolean isOk){
        this.isOk=isOk;
    }
}
