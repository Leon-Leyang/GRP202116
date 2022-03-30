package com.grp202116.consumerserver.pojo;

import java.math.BigInteger;

/**
 * The Class AnnotationDO, inherited from the LabelDO class, containing the basic information
 * controls the id information of the Annotation data object
 * including the getter and setter method
 */
public class AnnotationDO extends LabelDO {
    private BigInteger annotationId;

    /**
     * Get the id information of the Annotation data object
     * @return the id of the Annotation
     */
    public BigInteger getAnnotationId() {
        return annotationId;
    }

    /**
     * Set the id information of the Annotation data object
     * @param annotationId the newly set id
     */
    public void setAnnotationId(BigInteger annotationId) {
        this.annotationId = annotationId;
    }

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
