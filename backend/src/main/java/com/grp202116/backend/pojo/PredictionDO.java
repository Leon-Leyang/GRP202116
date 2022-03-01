package com.grp202116.backend.pojo;

/**
 * The class PredictionDO contains the basic information of the predicted labels, which inherited from the LabelDO class
 * controls the score and id information of the Prediction
 */
public class PredictionDO extends LabelDO {
    private String predictionId;
    private Double score;

    /**
     * Get the id of the Prediction label object
     * @return the id of the Prediction label object
     */
    public String getPredictionId() {
        return predictionId;
    }

    /**
     * Set the id of the Prediction label object
     * @param predictionId the set id of the prediction
     */
    public void setPredictionId(String predictionId) {
        this.predictionId = predictionId;
    }

    /**
     * Get the score of the Prediction label object
     * @return the score of the prediction
     */
    public Double getScore() {
        return score;
    }

    /**
     * Set the score of the Prediction label object
     * @param score the given score
     */
    public void setScore(Double score) {
        this.score = score;
    }
}
