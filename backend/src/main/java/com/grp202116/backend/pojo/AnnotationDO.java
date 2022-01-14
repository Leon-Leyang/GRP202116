package com.grp202116.backend.pojo;

import java.math.BigInteger;

public class AnnotationDO extends LabelDO {
    private BigInteger annotationId;

    public BigInteger getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(BigInteger annotationId) {
        this.annotationId = annotationId;
    }
}
