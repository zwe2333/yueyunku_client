package com.gdcp.yueyunku_client.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Asus on 2017/5/11.
 */

public class ThreadUtils {
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public  static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void runOnBackgroundThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }
}
