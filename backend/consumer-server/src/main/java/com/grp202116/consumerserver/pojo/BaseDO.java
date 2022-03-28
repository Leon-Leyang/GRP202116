package com.grp202116.consumerserver.pojo;

import java.util.Date;

/**
 * The class BaseDO define the basic information of the base object such as data and project
 * includes set the createTime of the obejct
 */
public class BaseDO {
    private Date createTime;

    /**
     * Get the createTime information of the object
     * @return return the createTime value
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Set the createTime value of the object
     * @param createTime the obtained createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
