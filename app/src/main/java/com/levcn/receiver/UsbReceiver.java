package com.levcn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.levcn.R;
import com.levcn.service.Server;

public class UsbReceiver extends BroadcastReceiver {

    private boolean BOOLEAN=false;


    //耳机的广播
    public static final String TAGLISTEN = "android.intent.action.HEADSET_PLUG";
    //usb线的广播
    private final static String TAGUSB = "android.hardware.usb.action.USB_STATE";
    //外设的广播
    public static final String TAGIN = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String TAGOUT = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //判断外设
        if (action.equals(TAGIN)) {
            LogUtils.eTag("sb","外设已经连接");
        }
        if (action.equals(TAGOUT)) {
            if (BOOLEAN) {
                LogUtils.eTag("sb","外设已经移除");
            }
        }
        //判断存储usb
        if (action.equals(TAGUSB)) {
            boolean connected = intent.getExtras().getBoolean("connected");
            if (connected) {
                LogUtils.eTag("sb","USB 已经连接");
                //启动服务
                new Server(10081).start();

                MediaPlayer mediaPlayer= MediaPlayer.create(context, R.raw.cc);
                mediaPlayer.start();

            } else {
                if (BOOLEAN) {
                    LogUtils.eTag("sb","USB 断开");
                }
            }
        }
        //判断耳机
        if (action.equals(TAGLISTEN)) {
            int intExtra = intent.getIntExtra("state", 0);
            // state --- 0代表拔出，1代表插入
            // name--- 字符串，代表headset的类型。
            // microphone -- 1代表这个headset有麦克风，0则没有
            // int i=intent.getIntExtra("",0);
            if (intExtra == 0) {
                if (BOOLEAN) {
                    LogUtils.eTag("sb","拔出耳机");
                }
            }
            if (intExtra == 1) {
                LogUtils.eTag("sb","耳机插入");
                int intType = intent.getIntExtra("microphone", 0);
                if (intType == 0) {
                    LogUtils.eTag("sb","没有麦克风");
                }
                if (intType == 1) {
                    LogUtils.eTag("sb","有话筒");
                }
            }
        }
        BOOLEAN=true;
    }

    public void registerUsbReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TAGLISTEN);
        filter.addAction(TAGUSB);
        filter.addAction(TAGIN);
        filter.addAction(TAGOUT);
        context.registerReceiver(this, filter);
    }

    public void unregisterUsbReceiver(Context context) {
        context.unregisterReceiver(this);
    }
}
