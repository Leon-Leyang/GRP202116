package com.grp202116.backend.pojo;

/**
 * The class ModelDO contains the basic information of the ml Model object
 * control the url, name and description value of the Model by setter and getter methods
 */
public class ModelDO extends ProjectDistinguishableDO{
    private String url;
    private String name;
    private String description;

    /**
     * Get the url value of the ml model
     * @return the url value
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url value of the ml model
     * @param url the given url value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the name value of the ml model
     * @return the name value of the model
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name value of the model
     * @param name the set name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the ml model
     * @return the description of the ml model
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the ml model
     * @param description the given description of the ml model
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
