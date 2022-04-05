package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.PredictionDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * This the mapper class of the prediction table,
 * which controls interactions with the database.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see PredictionDO
 */
@Mapper
public interface PredictionMapper extends BaseMapper {
    /**
     * Insert all the Predictions
     *
     * @param predictions the obtained Prediction
     */
    void insertAll(List<PredictionDO> predictions);

    /**
     * List the Prediction of certain data
     *
     * @param dataId the dataId
     * @return the Prediction of the data
     */
    List<PredictionDO> listByDataId(BigInteger dataId);

    /**
     * List the Prediction of certain project
     *
     * @param projectId the projectId
     * @return the Prediction of the project
     */
    List<PredictionDO> listByProjectId(BigInteger projectId);

    /**
     * Delete the Prediction of the project
     *
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * Alter prediction table to reset auto-increment from the largest number
     */
    void alter();

    /**
     * Set the status of a prediction to accepted
     *
     * @param predictionId the id of the specified prediction
     */
    void setAccepted(BigInteger predictionId);


    /**
     * Set the status of a prediction to not accepted
     *
     * @param predictionId the id of the specified prediction
     */
    void setNotAccepted(BigInteger predictionId);
}
