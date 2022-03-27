package com.grp202116.backend.pojo;

/**
 * The class PredictionDO contains the basic information of the predicted labels, which inherited from the LabelDO class
 * controls the score and id information of the Prediction
 */
public class PredictionDO extends LabelDO {
    private String predictionId;
    private String model;

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

    @Override
    public String toString() {
        return "Annotation{" +
                "predictionId=" + predictionId +
                ", type=" + getType() +
                ", value=" + getValue() +
                ", from_name=" + getFromName() +
                ", to_name=" + getToName() +
                ", create_time=" + getCreateTime() +
                ", update_time=" + getUpdateTime() +
                '}';
    }
}
