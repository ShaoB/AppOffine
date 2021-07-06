package com.levcn.bean;

/**
 * @author : shaoBin
 * date   : 2021/7/6 10:26
 * desc   :
 */
public class NeedDecisionInfo {
    private int id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务时间
     */
    private long taskTime;

    public NeedDecisionInfo(String taskName, long taskTime) {
        this.taskName = taskName;
        this.taskTime = taskTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }
}
