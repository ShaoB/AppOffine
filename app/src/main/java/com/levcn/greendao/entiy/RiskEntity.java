package com.levcn.greendao.entiy;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : shaoBin
 * date   : 2021/7/10 14:25
 * desc   : 隐患
 */
@Entity(
        nameInDb = "risk"
)
public class RiskEntity implements Parcelable {
    @Id(autoincrement = true)
    private Long _id;

    /**
     * 隐患级别
     */
    @NotNull
    private String riskRank;

    /**
     * 发现时间
     */
    @NotNull
    private String riskTime;
    /**
     * 描述
     */
    @NotNull
    private String riskDescribe;
    /**
     * 照片
     */
    @NotNull
    private String riskImg;
    /**
     * 发现者
     */
    @NotNull
    private String discoverer;

    protected RiskEntity(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readLong();
        }
        riskRank = in.readString();
        riskTime = in.readString();
        riskDescribe = in.readString();
        riskImg = in.readString();
        discoverer = in.readString();
    }

    @Generated(hash = 724079749)
    public RiskEntity(Long _id, @NotNull String riskRank, @NotNull String riskTime,
            @NotNull String riskDescribe, @NotNull String riskImg,
            @NotNull String discoverer) {
        this._id = _id;
        this.riskRank = riskRank;
        this.riskTime = riskTime;
        this.riskDescribe = riskDescribe;
        this.riskImg = riskImg;
        this.discoverer = discoverer;
    }

    @Generated(hash = 1034069593)
    public RiskEntity() {
    }

    public static final Creator<RiskEntity> CREATOR = new Creator<RiskEntity>() {
        @Override
        public RiskEntity createFromParcel(Parcel in) {
            return new RiskEntity(in);
        }

        @Override
        public RiskEntity[] newArray(int size) {
            return new RiskEntity[size];
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
        dest.writeString(riskRank);
        dest.writeString(riskTime);
        dest.writeString(riskDescribe);
        dest.writeString(riskImg);
        dest.writeString(discoverer);
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getRiskRank() {
        return this.riskRank;
    }

    public void setRiskRank(String riskRank) {
        this.riskRank = riskRank;
    }

    public String getRiskTime() {
        return this.riskTime;
    }

    public void setRiskTime(String riskTime) {
        this.riskTime = riskTime;
    }

    public String getRiskDescribe() {
        return this.riskDescribe;
    }

    public void setRiskDescribe(String riskDescribe) {
        this.riskDescribe = riskDescribe;
    }

    public String getRiskImg() {
        return this.riskImg;
    }

    public void setRiskImg(String riskImg) {
        this.riskImg = riskImg;
    }

    public String getDiscoverer() {
        return this.discoverer;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }
}
