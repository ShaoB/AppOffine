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




    //-----------------------行为------------------------------
    /**
     * 获取设备唯一id
     */
    public static final String GET_UNIQUE_DEVICE_ID = "getUniqueDeviceId";
    /**
     * 发送待见证任务
     */
    public static final String SEND_BACKLOG = "sendBacklog";
}
