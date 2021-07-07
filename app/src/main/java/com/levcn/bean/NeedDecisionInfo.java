package com.levcn.bean;

import com.levcn.greendao.entiy.TaskEntity;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class NeedDecisionInfo {
    private boolean flag;
    private int code;
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
