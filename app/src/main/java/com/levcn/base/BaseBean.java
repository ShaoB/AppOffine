package com.levcn.base;

import java.util.List;

public class BaseBean<T> {
    /**
     * 成功状态
     */
    private boolean flag;
    /**
     * 状态码
     */
    private int code;
    private String message;
    /**
     * 数据
     */
    private List<T> data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
