package com.grp202116.backend.pojo;

import java.math.BigInteger;
import java.util.Date;

public class DataDistinguishableDO extends ProjectDistinguishableDO{
    private BigInteger dataId;
    private String type;
    private Date updateTime;

    public BigInteger getDataId() {
        return dataId;
    }

    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
