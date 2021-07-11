package com.levcn.bean;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/10 17:35
 * desc   :
 */
public class ResultBean<T> {
    private boolean flag;
    private String action;
    /**
     * 数据
     */
    private List<T> data;

    public ResultBean(boolean flag, String action, List<T> data) {
        this.flag = flag;
        this.action = action;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
