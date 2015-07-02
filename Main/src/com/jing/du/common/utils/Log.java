package com.jing.du.common.utils;


/**
 * Created by charle-chen on 15/6/21.
 */
public class Log {

    private static final String TAG = "du_time";
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    private Log() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            android.util.Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            android.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            android.util.Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            android.util.Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            android.util.Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            android.util.Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            android.util.Log.i(tag, msg);
    }
}
