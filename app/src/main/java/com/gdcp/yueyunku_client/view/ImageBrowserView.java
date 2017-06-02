package com.gdcp.yueyunku_client.view;

import android.content.Intent;

import java.util.List;

/**
 * Created by Asus on 2017/5/14.
 */

public interface ImageBrowserView {
    Intent getDataIntent();

    void setImageBrowse(List<String> images, int position);
}
