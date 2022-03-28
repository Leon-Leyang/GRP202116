package com.grp202116.consumerserver.pojo;

/**
 * The class ModelDO contains the basic information of the ml Model object
 * control the url, name and description value of the Model by setter and getter methods
 */
public class ModelDO extends ProjectDistinguishableDO{
    private String url;
    private String version;
    private String description;
    private String modelRoot;

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
    public String getVersion() {
        return version;
    }

    /**
     * Set the name value of the model
     * @param version the set name value
     */
    public void setVersion(String version) {
        this.version = version;
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

    public String getModelRoot() {
        return modelRoot;
    }

    public void setModelRoot(String modelRoot) {
        this.modelRoot = modelRoot;
    }
}
