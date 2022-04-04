package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;

/**
 * The class ProjectDistinguishableDO contain the basic Project information
 * control the id of the project by getter and setter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ProjectDistinguishableDO extends BaseDO {
    private BigInteger projectId;

    /**
     * Get the id information of the project
     *
     * @return the id value of the project
     */
    public BigInteger getProjectId() {
        return projectId;
    }

    /**
     * Set the project id information
     *
     * @param projectId the newly set project id
     */
    public void setProjectId(BigInteger projectId) {
        this.projectId = projectId;
    }
}
