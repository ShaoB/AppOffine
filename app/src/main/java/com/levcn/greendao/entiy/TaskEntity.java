package com.levcn.greendao.entiy;

import android.os.Parcel;
import android.os.Parcelable;

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
public class TaskEntity implements Parcelable {
    @Id(autoincrement = true)
    private Long _id;
    /**
     * 任务id
     */
    private String f_GZNR;
    private String f_GZFZR_NAME;
    private String f_PZR;
    private String f_PZR_NAME;
    private String f_GZDD_NAME;
    private String f_FXDJ_NAME;
    private String f_PZZT_NAME;
    private String id_;
    private int state;//0 待见证 1 待上传 2已完成
    private String imgUrls;
    private String InspectContent;//检查内容
    private String inspectTime;//检查时间

    protected TaskEntity(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readLong();
        }
        f_GZNR = in.readString();
        f_GZFZR_NAME = in.readString();
        f_PZR = in.readString();
        f_PZR_NAME = in.readString();
        f_GZDD_NAME = in.readString();
        f_FXDJ_NAME = in.readString();
        f_PZZT_NAME = in.readString();
        id_ = in.readString();
        state = in.readInt();
        imgUrls = in.readString();
        InspectContent = in.readString();
        inspectTime = in.readString();
    }

    @Generated(hash = 1204568228)
    public TaskEntity(Long _id, String f_GZNR, String f_GZFZR_NAME, String f_PZR,
            String f_PZR_NAME, String f_GZDD_NAME, String f_FXDJ_NAME, String f_PZZT_NAME,
            String id_, int state, String imgUrls, String InspectContent,
            String inspectTime) {
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
        this.imgUrls = imgUrls;
        this.InspectContent = InspectContent;
        this.inspectTime = inspectTime;
    }

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(_id);
        }
        dest.writeString(f_GZNR);
        dest.writeString(f_GZFZR_NAME);
        dest.writeString(f_PZR);
        dest.writeString(f_PZR_NAME);
        dest.writeString(f_GZDD_NAME);
        dest.writeString(f_FXDJ_NAME);
        dest.writeString(f_PZZT_NAME);
        dest.writeString(id_);
        dest.writeInt(state);
        dest.writeString(imgUrls);
        dest.writeString(InspectContent);
        dest.writeString(inspectTime);
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

    public String getImgUrls() {
        return this.imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getInspectContent() {
        return this.InspectContent;
    }

    public void setInspectContent(String InspectContent) {
        this.InspectContent = InspectContent;
    }

    public String getInspectTime() {
        return this.inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }
}
