package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;

/**
 * The class ModelDO contains the basic information of the ml Model object
 * control the url, name and description value of the Model by setter and getter methods
 */
public class ModelDO extends ProjectDistinguishableDO{
    private BigInteger modelId;
    private String modelPath;
    private String version;
    private String description;
    private String modelRoot;
    private String type;
    private String params;
    private String labelsPath;

    public BigInteger getModelId() {
        return modelId;
    }

    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    /**
     * Get the url value of the ml model
     * @return the url value
     */
    public String getModelPath() {
        return modelPath;
    }

    /**
     * Set the url value of the ml model
     * @param modelPath the given url value
     */
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getLabelsPath() {
        return labelsPath;
    }

    public void setLabelsPath(String labelsPath) {
        this.labelsPath = labelsPath;
    }
}
