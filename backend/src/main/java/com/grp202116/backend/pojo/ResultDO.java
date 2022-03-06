package com.grp202116.backend.pojo;

import java.math.BigInteger;

public class ResultDO {
    private BigInteger resultId;
    private BigInteger labelId;
    private String type;
    private String fromName;
    private String toName;
    private String value;
    private String origin;
    private BigInteger originalHeight;
    private BigInteger getOriginalWeight;

    public BigInteger getResultId() {
        return resultId;
    }

    public void setResultId(BigInteger resultId) {
        this.resultId = resultId;
    }

    public BigInteger getLabelId() {
        return labelId;
    }

    public void setLabelId(BigInteger labelId) {
        this.labelId = labelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BigInteger getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(BigInteger originalHeight) {
        this.originalHeight = originalHeight;
    }

    public BigInteger getGetOriginalWeight() {
        return getOriginalWeight;
    }

    public void setGetOriginalWeight(BigInteger getOriginalWeight) {
        this.getOriginalWeight = getOriginalWeight;
    }
}
