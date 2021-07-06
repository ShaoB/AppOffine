package com.levcn.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.levcn.service.Server;

/**
 * @author : shaoBin
 * date   : 2021/7/6 15:08
 * desc   :
 */
public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @SuppressLint("StaticFieldLeak")
    public static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;

        //启动服务
        new Server(10081).start();
    }

    public static MyApp getApplication() {
        return instance;
    }
}
