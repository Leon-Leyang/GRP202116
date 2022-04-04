package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;

/**
 * The Class AnnotationDO, inherited from the LabelDO class, containing the basic information
 * controls the id information of the Annotation data object
 * including the getter and setter method
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class AnnotationDO extends LabelDO {
    private BigInteger annotationId;

    /**
     * Get the id information of the Annotation data object
     *
     * @return the id of the Annotation
     */
    public BigInteger getAnnotationId() {
        return annotationId;
    }

    /**
     * Set the id information of the Annotation data object
     *
     * @param annotationId the newly set id
     */
    public void setAnnotationId(BigInteger annotationId) {
        this.annotationId = annotationId;
    }

    /**
     * This is used for exporting functions
     *
     * @return the information of a certain annotation
     */
    @Override
    public String toString() {
        return "Annotation{" +
                "annotationId=" + annotationId +
                ", type=" + getType() +
                ", result=" + getResult() +
                ", create_time=" + getCreateTime() +
                ", update_time=" + getUpdateTime() +
                '}';
    }
}
