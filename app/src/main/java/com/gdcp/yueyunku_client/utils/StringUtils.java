package com.gdcp.yueyunku_client.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asus on 2017/5/11.
 */

public class StringUtils {

    /**
     *
     * 判断手机号是否合法
     * */
    public static boolean isMobileNo(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,9])|(15[^4,\\D])|(17[0,3,5-8])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
}
