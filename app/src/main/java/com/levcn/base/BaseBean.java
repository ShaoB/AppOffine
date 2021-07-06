package com.levcn.base;

import com.blankj.utilcode.util.Utils;
import com.levcn.greendao.entiy.TaskEntity;

import java.util.List;

public class BaseBean<T> {
    private boolean flag;//成功状态
    private int code;//状态码
    private String message;
    private List<TaskEntity> data;

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

    public List<TaskEntity> getData() {
        return data;
    }

    public void setData(List<TaskEntity> data) {
        this.data = data;
    }
}
