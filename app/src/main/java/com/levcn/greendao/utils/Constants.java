package com.levcn.greendao.utils;

/**
 * @author : shaoBin
 * date   : 2021/7/7 10:31
 * desc   :
 */
public class Constants {

    /**
     * 代办任务
     */
    public static final int TASK_STATE_NEED_DECISION = 0;
    /**
     * 待上传任务
     */
    public static final int TASK_STATE_NEED_UPDATE = 1;
    /**
     * 已办任务
     */
    public static final int TASK_STATE_COMPLETE = 2;


    /**
     * 名称
     */
    public static final String GENERAL_NAME = "appOffline";

    /**
     * 缓存根目录
     */
    public static final String CACHE_BASE_PATH = "/" + GENERAL_NAME + "/";

    /**
     * 缓存图片目录
     */
    public static final String CACHE_IMG_PATH = CACHE_BASE_PATH + "img/";


    //-----------------------行为------------------------------
    /**
     * 获取设备唯一id
     */
    public static final String GET_UNIQUE_DEVICE_ID = "getUniqueDeviceId";
    /**
     * 发送待见证任务
     */
    public static final String SEND_BACKLOG = "sendBacklogTask";
    /**
     * 发送待上传任务
     */
    public static final String SEND_UPDATE_TASK = "sendUpdateTask";
}
