package com.levcn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;

import com.blankj.utilcode.util.LogUtils;
import com.levcn.R;

public class UsbReceiver extends BroadcastReceiver {

    private boolean BOOLEAN=false;

    //usb线的广播
    private final static String TAGUSB = "android.hardware.usb.action.USB_STATE";
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //判断存储usb
        if (action.equals(TAGUSB)) {
            boolean connected = intent.getExtras().getBoolean("connected");
            if (connected) {
                LogUtils.eTag("sb","USB 已经连接");
                mediaPlayer.start();

            } else {
                if (BOOLEAN) {
                    LogUtils.eTag("sb","USB 断开");
                    mediaPlayer.stop();
                }
            }
        }
        BOOLEAN=true;
    }

    public void registerUsbReceiver(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.cc);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TAGUSB);
        context.registerReceiver(this, filter);
    }

    public void unregisterUsbReceiver(Context context) {
        context.unregisterReceiver(this);
    }
}
