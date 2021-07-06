package com.levcn.manager;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.levcn.base.BaseActivity;

import java.util.LinkedList;

/**
 * @author : shaoBin
 * date   : 2021/7/5 17:00
 * desc   : activity 管理
 */
public class ActivityCollector {
    /**
     * 当前在前台的activity
     */
    @SuppressLint("StaticFieldLeak")
    private static Activity mCurrentActivity;

    public static LinkedList<Activity> activities = new LinkedList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭所有Activity,只保留目标Activity
     */
    public static void exitAllActivityExceptTargetActivity(Class<?> activityClass) {
        for (Activity activity : activities) {
            if (!activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭指定的Activity
     */
    public static void removeTargetActivity(Class<?> activityClass) {
        if (activities == null) {
            return;
        }
        for (Activity activity : activities) {
            if (activity.getClass().equals(activityClass)) {
                if (activity instanceof BaseActivity) {
                    ((BaseActivity) activity).finish();
                }
            }

        }
    }

    /**
     * 判断指定的Activity是否存在
     */
    public static Boolean isActivityExistence(Class<?> activityClass) {
        if (activities == null) {
            return false;
        }
        for (Activity activity : activities) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获得指定的Activity
     */
    public static Activity getTargetActivity(Class<?> activityClass) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(activityClass)) {
                return activity;
            }
        }
        return null;
    }
}
