package com.levcn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.levcn.R;

public class UsbReceiver extends BroadcastReceiver {

    private boolean bOOLEAN = false;

    /**
     * usb线的广播
     */
    private final static String TAGUSB = "android.hardware.usb.action.USB_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //判断存储usb
        if (action != null) {
            if (action.equals(TAGUSB)) {
                Bundle bundle = intent.getExtras();
                if (bundle == null) {
                    return;
                }
                boolean connected = intent.getExtras().getBoolean("connected");
                if (connected) {
                    LogUtils.eTag("sb", "USB 已经连接");

                } else {
                    if (bOOLEAN) {
                        LogUtils.eTag("sb", "USB 断开");
                    }
                }
            }
            bOOLEAN = true;
        }
    }

    public void registerUsbReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TAGUSB);
        context.registerReceiver(this, filter);
    }

    public void unregisterUsbReceiver(Context context) {
        context.unregisterReceiver(this);
    }
}
