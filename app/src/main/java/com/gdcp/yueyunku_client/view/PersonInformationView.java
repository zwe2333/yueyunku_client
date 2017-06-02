package com.gdcp.yueyunku_client.view;

/**
 * Created by Asus on 2017/5/17.
 */

public interface PersonInformationView {
    void onUploadHeadSuccess();

    void onUploadHeadFail();

    void onSetMsg(String username, String head, String gender,String signature,String area);
}
