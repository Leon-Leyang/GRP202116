package com.grp202116.consumerserver.pojo;

import java.util.Date;

/**
 * The class ProjectDO contain the basic information of the project data object
 * controls the type, updateTime, name and description value of the project object using setter and getter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ProjectDO extends ProjectDistinguishableDO {
    private String type;
    private Date updateTime;
    private String name;
    private String description;
    private String configs;

    /**
     * Get the type of the project object
     *
     * @return the type of the project
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the project
     *
     * @param type the set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the updateTime of the project data object
     *
     * @return the updateTime of the project
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the updateTime of the project
     *
     * @param updateTime the set updateTime of the project
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Get the name value of the project data object
     *
     * @return the name value
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name value of the project
     *
     * @param name the set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the project data object
     *
     * @return the description of the project
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the project
     *
     * @param description the set description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the configs of a certain project
     *
     * @return the {@link #configs}
     */
    public String getConfigs() {
        return configs;
    }

    /**
     * Set the configs of a certain project
     *
     * @param configs the configs to be updated
     */
    public void setConfigs(String configs) {
        this.configs = configs;
    }
}
