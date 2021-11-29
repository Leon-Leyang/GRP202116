package com.grp202116.backend.pojo;

import java.math.BigInteger;

public class ProjectDistinguishableDO extends BaseDO{
    private BigInteger projectId;

    public BigInteger getProjectId() {
        return projectId;
    }

    public void setProjectId(BigInteger projectId) {
        this.projectId = projectId;
    }
}
