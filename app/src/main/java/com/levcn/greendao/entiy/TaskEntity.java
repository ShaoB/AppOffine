package com.levcn.greendao.entiy;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : shaoBin
 * date   : 2021/7/6 17:33
 * desc   :
 */

@Entity(
        nameInDb = "task"
)
public class TaskEntity {
    @Id(autoincrement = true)
    private Long _id;
    /**
     * 任务id
     */
    @NotNull
    private String task_id;
    /**
     * 任务名称
     */
    @NotNull
    private String task_name;
    /**
     * 任务时间
     */
    @NotNull
    private String task_time;
@Generated(hash = 1325025602)
public TaskEntity(Long _id, @NotNull String task_id, @NotNull String task_name,
        @NotNull String task_time) {
    this._id = _id;
    this.task_id = task_id;
    this.task_name = task_name;
    this.task_time = task_time;
}
@Generated(hash = 397975341)
public TaskEntity() {
}
public Long get_id() {
    return this._id;
}
public void set_id(Long _id) {
    this._id = _id;
}
public String getTask_id() {
    return this.task_id;
}
public void setTask_id(String task_id) {
    this.task_id = task_id;
}
public String getTask_name() {
    return this.task_name;
}
public void setTask_name(String task_name) {
    this.task_name = task_name;
}
public String getTask_time() {
    return this.task_time;
}
public void setTask_time(String task_time) {
    this.task_time = task_time;
}
}
