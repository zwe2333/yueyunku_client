package com.gdcp.yueyunku_client.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Asus on 2017/5/14.
 */

public class WindowUtils {
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 屏幕宽度
     */
    private int screenHeight;

    private static class WindowUtilInstance{

        private static WindowUtils instance = new WindowUtils();

    }

    /**
     * 获得单例对象
     * @return
     */
    public static WindowUtils getInstance(){
        return WindowUtilInstance.instance;
    }


    private WindowUtils(){}

    /**
     * 获取屏幕的宽
     * @param context Context
     * @return 屏幕的宽
     */
    public int getScreenWidth(Activity context){
        if(context == null){
            return 0;
        }
        if(screenWidth != 0){
            return screenWidth;
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 获取屏幕的高
     * @param context Context
     * @return 屏幕的高
     */
    public int getScreenHeight(Activity context){
        if(context == null){
            return 0;
        }
        if(screenHeight != 0){
            return  screenHeight;
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 获取控件的位置
     * @param view 控件View
     * @return int[] x,y
     */
    public int[] getViewLocation(View view){
        int[] location = new int[2]; //获取筛选按钮的x坐标
        view.getLocationOnScreen(location);
        return location;
    }


    public int getStateBarHeight(Context context){
        Rect rect= new Rect();
        Activity activity = (Activity) context;
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        return  statusBarHeight;
    }
}
