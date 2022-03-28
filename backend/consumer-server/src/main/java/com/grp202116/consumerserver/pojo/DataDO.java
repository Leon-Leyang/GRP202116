package com.grp202116.consumerserver.pojo;

/**
 * The class DataDO which inherited from the DataDistinguishableDO contain the basic information of the data
 * control the url value of the data object by getter and setter method
 */
public class DataDO extends DataDistinguishableDO {
    private String url;
    private boolean isAnnotated;

    /**
     * Get the Url value of the data
     * @return the Url value of the data object
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the Url value of the data
     * @param url the set Url value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAnnotated() {
        return isAnnotated;
    }

    public void setAnnotated(boolean annotated) {
        isAnnotated = annotated;
    }
}
