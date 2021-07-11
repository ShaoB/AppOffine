package com.levcn.bean;

import com.levcn.greendao.entiy.TaskEntity;

import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class NeedDecisionInfo {
    /**
     * 行为
     */
    private String action;
    private List<TaskEntity> data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<TaskEntity> getData() {
        return data;
    }

    public void setData(List<TaskEntity> data) {
        this.data = data;
    }
}
