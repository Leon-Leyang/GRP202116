package com.grp202116.consumerserver.pojo;

/**
 * The class DataDO which inherited from the DataDistinguishableDO contain the basic information of the data
 * control the url value of the data object by getter and setter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class DataDO extends DataDistinguishableDO {
    private String url;
    private boolean isAnnotated;
    private boolean isPredicted;

    /**
     * Get the Url value of the data
     *
     * @return the Url value of the data object
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the Url value of the data
     *
     * @param url the set Url value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the status of annotated
     *
     * @return true if annotated false otherwise
     */
    public boolean isAnnotated() {
        return isAnnotated;
    }

    /**
     * Set the status of annotated
     *
     * @param annotated the status to be updated
     */
    public void setAnnotated(boolean annotated) {
        isAnnotated = annotated;
    }

    /**
     * Get the status of predicted
     *
     * @return true if predicted false otherwise
     */
    public boolean isPredicted() {
        return isPredicted;
    }

    /**
     * Set the status of predicted
     *
     * @param predicted the status to be updated
     */
    public void setPredicted(boolean predicted) {
        isPredicted = predicted;
    }
}
