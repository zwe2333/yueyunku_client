package com.gdcp.yueyunku_client.presenter;

import java.util.List;

/**
 * Created by Asus on 2017/5/14.
 */

public interface ImageBrowserPresenter {
    void loadImage();

    void setPosition(int position);

    List<String> getImage();
}
