package com.grp202116.backend.statistics;

import com.grp202116.backend.pojo.PredictionDO;

import java.math.BigInteger;
import java.util.List;

public class Statistics {
    BigInteger projectId;
    public Statistics(BigInteger projectId){
        this.projectId = projectId;
    }

    public BigInteger getDatasNumber() {
        return datasNumber;
    }

    public void setDatasNumber(BigInteger datasNumber) {
        this.datasNumber = datasNumber;
    }

    public BigInteger getAnnotationsNumber() {
        return annotationsNumber;
    }

    public void setAnnotationsNumber(BigInteger annotationsNumber) {
        this.annotationsNumber = annotationsNumber;
    }

    public BigInteger getPredictionsNumber() {
        return predictionsNumber;
    }

    public void setPredictionsNumber(BigInteger predictionsNumber) {
        this.predictionsNumber = predictionsNumber;
    }

    public Float getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Float completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public List<PredictionDO> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionDO> predictions) {
        this.predictions = predictions;
    }

    BigInteger datasNumber;
    BigInteger annotationsNumber;
    BigInteger predictionsNumber;
    Float completionPercentage;
    List<PredictionDO> predictions;
}
