package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.PredictionDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * The PredictionMapper define the actions or the operations of the Prediction
 */
@Mapper
public interface PredictionMapper extends BaseMapper {
    /**
     * Insert all the Predictions
     * @param predictions the obtained Prediction
     */
    void insertAll(List<PredictionDO> predictions);

    /**
     * List the Prediction of certain data
     * @param dataId the dataId
     * @return the Prediction of the data
     */
    List<PredictionDO> listByDataId(BigInteger dataId);

    /**
     * List the Prediction of certain project
     * @param projectId the projectId
     * @return the Prediction of the project
     */
    List<PredictionDO> listByProjectId(BigInteger projectId);

    /**
     * Delete the Prediction of the project
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    void alter();

    void setAccepted(BigInteger predictionId);

    void setNotAccepted(BigInteger predictionId);
}
