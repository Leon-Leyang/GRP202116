package com.grp202116.consumerserver.pojo;

/**
 * The class PredictionDO contains the basic information of the predicted labels, which inherited from the LabelDO class
 * controls the score and id information of the Prediction
 */
public class PredictionDO extends LabelDO {
    private String predictionId;
    private String model;
    private boolean isAccepted;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "predictionId=" + predictionId +
                ", type=" + getType() +
                ", result=" + getResult() +
                ", is_accepted=" + isAccepted +
                ", create_time=" + getCreateTime() +
                ", update_time=" + getUpdateTime() +
                '}';
    }
}
