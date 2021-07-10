package com.levcn.bean;

/**
 * @author : shaoBin
 * date   : 2021/7/7 14:00
 * desc   :
 */
public class ImageInfo {

    /**
     * 0 图片 1添加
     */
    private int type;
    private String url;
    private String localPath;
    /**
     * 0 展示 1 不展示
     */
    private int showType;

    public ImageInfo(String url, String localPath) {
        this.url = url;
        this.localPath = localPath;
    }

    public ImageInfo(String url, int showType) {
        this.url = url;
        this.showType = showType;
    }

    public ImageInfo(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
