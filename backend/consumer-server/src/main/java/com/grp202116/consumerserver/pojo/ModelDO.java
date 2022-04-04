package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;

/**
 * The class ModelDO contains the basic information of the ml Model object
 * control the url, name and description value of the Model by setter and getter methods
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ModelDO extends ProjectDistinguishableDO {
    private BigInteger modelId;
    private String modelPath;
    private String version;
    private String description;
    private String modelRoot;
    private String type;
    private String params;
    private String labelsPath;

    /**
     * Get the id of a model
     *
     * @return the id
     */
    public BigInteger getModelId() {
        return modelId;
    }

    /**
     * Set the id of a model
     *
     * @param modelId an id of type {@link BigInteger}
     */
    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    /**
     * Get the url value of the ml model
     *
     * @return the url value
     */
    public String getModelPath() {
        return modelPath;
    }

    /**
     * Set the url value of the ml model
     *
     * @param modelPath the given url value
     */
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    /**
     * Get the version of the ml model
     *
     * @return the version of the model
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version of the model
     *
     * @param version the set version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get the description of the ml model
     *
     * @return the description of the ml model
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the ml model
     *
     * @param description the given description of the ml model
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the root path of a ml model
     *
     * @return the set url of root path
     */
    public String getModelRoot() {
        return modelRoot;
    }

    /**
     * Set the root path of a ml model
     *
     * @param modelRoot the url of root path
     */
    public void setModelRoot(String modelRoot) {
        this.modelRoot = modelRoot;
    }

    /**
     * Get the type of a ml model
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the type of a ml model
     *
     * @param type the set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the pre-params of a ml model
     *
     * @return the params
     */
    public String getParams() {
        return params;
    }

    /**
     * Set the params of a ml model
     *
     * @param params the params
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * Get the labels path of a ml model
     *
     * @return the labels path
     */
    public String getLabelsPath() {
        return labelsPath;
    }

    /**
     * Set the labels path of a ml model
     *
     * @param labelsPath the labels path
     */
    public void setLabelsPath(String labelsPath) {
        this.labelsPath = labelsPath;
    }
}
