package com.levcn.listener;

import android.view.View;

import java.util.Calendar;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:10
 * desc   :
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    private long lastClickTime = 0;
    public static final int MIN_CLICK_DELAY_TIME = 1000;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - this.lastClickTime > MIN_CLICK_DELAY_TIME) {
            this.lastClickTime = currentTime;
            this.onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
