package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;
import java.util.Date;

/**
 * The class DataDistinguishableDO Contain the basic information of the Data object
 * controls the id, type, and updateTime information by getter and setter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class DataDistinguishableDO extends ProjectDistinguishableDO {
    private BigInteger dataId;
    private String type;
    private Date updateTime;

    /**
     * Get the id information of the data object
     *
     * @return the id information
     */
    public BigInteger getDataId() {
        return dataId;
    }

    /**
     * Set the id information of the Data
     *
     * @param dataId the newly set id
     */
    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    /**
     * Get the type of the Data, either text or picture
     *
     * @return the type of the data
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the data object
     *
     * @param type the type of the data
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the updateTime value of the data
     *
     * @return the updateTime value
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the updateTime value of the data object
     *
     * @param updateTime the updated updateTime value of the data
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
