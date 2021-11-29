package com.grp202116.backend.pojo;

public class PredictionDO extends LabelDO {
    private String predictionId;
    private Double score;

    public String getPredictionId() {
        return predictionId;
    }

    public void setPredictionId(String predictionId) {
        this.predictionId = predictionId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
