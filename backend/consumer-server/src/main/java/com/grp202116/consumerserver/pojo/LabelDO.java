package com.grp202116.consumerserver.pojo;


/**
 * The class LabelDO define the basic information of the Label object
 * controls the value, fromName and toName value by getter and setter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class LabelDO extends DataDistinguishableDO {
    private String result;

    /**
     * Get the result of a certain label
     *
     * @return the result as {@link String}
     */
    public String getResult() {
        return result;
    }

    /**
     * Set the result of a certain label
     *
     * @param result the result to be updated
     */
    public void setResult(String result) {
        this.result = result;
    }

}
