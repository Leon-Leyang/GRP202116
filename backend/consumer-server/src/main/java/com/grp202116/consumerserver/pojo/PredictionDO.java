package com.grp202116.consumerserver.pojo;

/**
 * The class PredictionDO contains the basic information of the predicted labels, which inherited from the LabelDO class
 * controls the score and id information of the Prediction
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class PredictionDO extends LabelDO {
    private String predictionId;
    private String model;
    private boolean isAccepted;

    /**
     * Get the id of the Prediction label object
     *
     * @return the id of the Prediction label object
     */
    public String getPredictionId() {
        return predictionId;
    }

    /**
     * Set the id of the Prediction label object
     *
     * @param predictionId the set id of the prediction
     */
    public void setPredictionId(String predictionId) {
        this.predictionId = predictionId;
    }

    /**
     * Get the model of a prediction
     *
     * @return the model version
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the model of a prediction
     *
     * @param model the model version
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Get the status of {@link #isAccepted}
     *
     * @return the {@link #isAccepted}
     */
    public boolean isAccepted() {
        return isAccepted;
    }

    /**
     * Set the status of {@link #isAccepted}
     *
     * @param accepted the status of accepting a prediction
     */
    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    /**
     * This method is used for exporting functionality
     *
     * @return the information of this prediction
     */
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
