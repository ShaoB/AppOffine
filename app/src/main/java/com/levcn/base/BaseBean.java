package com.levcn.base;

import java.util.List;

public class BaseBean<T> {

    /**
     * 行为
     */
    private String action;
    /**
     * 数据
     */
    private List<T> data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
