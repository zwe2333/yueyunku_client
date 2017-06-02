package com.gdcp.yueyunku_client.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.gdcp.yueyunku_client.R;

/**
 * Created by Asus on 2017/5/11.
 */

public class CountDownTimerUtils extends CountDownTimer{

    private Button mButton;
    private Context mContext;

    public CountDownTimerUtils(long millisInFuture, long countDownInterval, Button button, Context context) {
        super(millisInFuture, countDownInterval);
        mButton=button;
        mContext=context;
    }


    @Override
    public void onTick(long l) {
        mButton.setBackground(mContext.getDrawable(R.drawable.bg_btn_noclick));
        mButton.setClickable(false);
        mButton.setText(l/1000+"s");
    }

    @Override
    public void onFinish() {
        mButton.setBackground(mContext.getDrawable(R.drawable.bg_btn_selector));
        //重新给Button设置文字
        mButton.setText("重新获取验证码");
        //设置可点击
        mButton.setClickable(true);
    }
}
