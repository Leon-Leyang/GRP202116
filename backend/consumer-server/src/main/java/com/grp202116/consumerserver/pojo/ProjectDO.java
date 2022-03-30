package com.grp202116.consumerserver.pojo;

import java.util.Date;

/**
 * The class ProjectDO contain the basic information of the project data object
 * controls the type, updateTime, name and description value of the project object using setter and getter method
 */
public class ProjectDO extends ProjectDistinguishableDO{
    private String type;
    private Date updateTime;
    private String name;
    private String description;
    private String configs;
    private String labelsPath;

    /**
     * Get the type of the project object
     * @return the type of the project
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the project
     * @param type the set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the updateTime of the project data object
     * @return the updateTime of the project
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the updateTime of the project
     * @param updateTime the set updateTime of the project
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Get the name value of the project data object
     * @return the name value
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name value of the project
     * @param name the set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the project data object
     * @return the description of the project
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the project
     * @param description the set description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getLabelsPath() {
        return labelsPath;
    }

    public void setLabelsPath(String labelsPath) {
        this.labelsPath = labelsPath;
    }
}
