package com.grp202116.consumerserver.pojo;


/**
 * The class LabelDO define the basic information of the Label object
 * controls the value, fromName and toName value by getter and setter method
 */
public class LabelDO extends DataDistinguishableDO{
    private String fromName;
    private String toName;
    private String value;

    /**
     * Get the fromName information of the label
     * @return the fromName of the label
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * Set the formName information of the label
     * @param fromName the newly set formName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * Get the toName information of the label
     * @return the toName value of the label
     */
    public String getToName() {
        return toName;
    }

    /**
     * Set the toName information of the label
     * @param toName the newly set toName value
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
