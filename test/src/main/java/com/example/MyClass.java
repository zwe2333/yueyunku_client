package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    public static boolean isMobileNo(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,9])|(15[^4,\\D])|(17[0,3,5-8])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
    public static void ts(final String a){



    }
    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        System.out.println(sdf.format(date));

    }
}
