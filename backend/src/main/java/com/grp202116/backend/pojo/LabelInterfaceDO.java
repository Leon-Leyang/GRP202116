package com.grp202116.backend.pojo;

/**
 * The class LabelInterfaceDO contains the information of the label interface defined by the user
 * controls the code value of the label interface by getter and setter method
 */
public class LabelInterfaceDO extends ProjectDistinguishableDO{
    private String code;

    /**
     * Get the code value of the label interface
     * @return the code value
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the code value of the label interface
     * @param code the code defined by the user
     */
    public void setCode(String code) {
        this.code = code;
    }
}
