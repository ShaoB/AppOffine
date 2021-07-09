package com.levcn.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.levcn.greendao.DaoManager;
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

        DaoManager.getInstance().init(this);

        //启动服务
        new Server(10081).start();

        String uniqueDeviceId = DeviceUtils.getUniqueDeviceId();
        LogUtils.eTag("sb","设备唯一ID："+ uniqueDeviceId);
    }

    public static MyApp getApplication() {
        return instance;
    }
}
