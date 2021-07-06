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
    private String f_GZNR;
    private String f_GZFZR_NAME;
    private String f_PZR;
    private String f_PZR_NAME;
    private String f_GZDD_NAME;
    private String f_FXDJ_NAME;
    private String f_PZZT_NAME;
    private String id_;
    private int state;//0 待见证 1 待上传
@Generated(hash = 1421727295)
public TaskEntity(Long _id, @NotNull String f_GZNR, String f_GZFZR_NAME,
        String f_PZR, String f_PZR_NAME, String f_GZDD_NAME, String f_FXDJ_NAME,
        String f_PZZT_NAME, String id_, int state) {
    this._id = _id;
    this.f_GZNR = f_GZNR;
    this.f_GZFZR_NAME = f_GZFZR_NAME;
    this.f_PZR = f_PZR;
    this.f_PZR_NAME = f_PZR_NAME;
    this.f_GZDD_NAME = f_GZDD_NAME;
    this.f_FXDJ_NAME = f_FXDJ_NAME;
    this.f_PZZT_NAME = f_PZZT_NAME;
    this.id_ = id_;
    this.state = state;
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
public String getF_GZNR() {
    return this.f_GZNR;
}
public void setF_GZNR(String f_GZNR) {
    this.f_GZNR = f_GZNR;
}
public String getF_GZFZR_NAME() {
    return this.f_GZFZR_NAME;
}
public void setF_GZFZR_NAME(String f_GZFZR_NAME) {
    this.f_GZFZR_NAME = f_GZFZR_NAME;
}
public String getF_PZR() {
    return this.f_PZR;
}
public void setF_PZR(String f_PZR) {
    this.f_PZR = f_PZR;
}
public String getF_PZR_NAME() {
    return this.f_PZR_NAME;
}
public void setF_PZR_NAME(String f_PZR_NAME) {
    this.f_PZR_NAME = f_PZR_NAME;
}
public String getF_GZDD_NAME() {
    return this.f_GZDD_NAME;
}
public void setF_GZDD_NAME(String f_GZDD_NAME) {
    this.f_GZDD_NAME = f_GZDD_NAME;
}
public String getF_FXDJ_NAME() {
    return this.f_FXDJ_NAME;
}
public void setF_FXDJ_NAME(String f_FXDJ_NAME) {
    this.f_FXDJ_NAME = f_FXDJ_NAME;
}
public String getF_PZZT_NAME() {
    return this.f_PZZT_NAME;
}
public void setF_PZZT_NAME(String f_PZZT_NAME) {
    this.f_PZZT_NAME = f_PZZT_NAME;
}
public String getId_() {
    return this.id_;
}
public void setId_(String id_) {
    this.id_ = id_;
}
public int getState() {
    return this.state;
}
public void setState(int state) {
    this.state = state;
}

}
