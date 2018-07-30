package com.socks.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

public class KLog {
    public static void d(String tag,@Nullable Object object) {
        Logger.t(tag).d(object);
    }
    public static void d(@Nullable Object object) {
        Logger.d(object);
    }
    public static void d(String tag, String message) {
        Logger.t(tag).d(message);
    }

    public static void d(String message) {
       Logger.d(message);
    }


    public static void e(String tag, String message) {
        Logger.t(tag).e(message);
    }

    public static void e(String message) {
        Logger.e(message);
    }


    public static void i(String tag, String message) {
        Logger.t(tag).i(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }


    public static void w(String tag, String message) {
        Logger.t(tag).w(message);
    }

    public static void w(String message) {
        Logger.d(message);
    }


    public static void v(String tag, String message) {
        Logger.t(tag).d(message);
    }

    public static void v(String message) {
        Logger.d(message);
    }

    public static void wtf(String tag, String message) {
        Logger.t(tag).wtf(message);
    }

    public static void wtf(String message) {
        Logger.wtf(message);
    }

}
