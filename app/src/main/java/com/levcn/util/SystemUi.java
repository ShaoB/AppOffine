package com.levcn.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * @author shaobin
 */
public class SystemUi {
    public static void fixSystemUI(Activity ac, boolean isTextColor) {
        //Android 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isTextColor) {
                //获取最顶层的view
                ac.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
            } else {
                ac.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
            }
            ac.getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }
}
