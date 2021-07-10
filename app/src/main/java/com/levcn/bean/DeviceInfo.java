package com.levcn.bean;

/**
 * @author : shaoBin
 * date   : 2021/7/10 17:33
 * desc   :
 */
public class DeviceInfo {
    /**
     * 设备唯一ID
     */
    private String uniqueDeviceID;

    public DeviceInfo(String uniqueDeviceID) {
        this.uniqueDeviceID = uniqueDeviceID;
    }

    public String getUniqueDeviceID() {
        return uniqueDeviceID;
    }

    public void setUniqueDeviceID(String uniqueDeviceID) {
        this.uniqueDeviceID = uniqueDeviceID;
    }
}
