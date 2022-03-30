package com.grp202116.consumerserver.pojo;


/**
 * The class LabelDO define the basic information of the Label object
 * controls the value, fromName and toName value by getter and setter method
 */
public class LabelDO extends DataDistinguishableDO{
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
