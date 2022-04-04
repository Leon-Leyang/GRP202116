package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.PredictionMapper;
import com.grp202116.consumerserver.pojo.PredictionDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * This is the controller of predictions,
 * including methods of selection, insertion and deletion.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see PredictionMapper
 */
@RestController
public class PredictionController {
    @Resource
    private PredictionMapper predictionMapper;

    /**
     * List the Prediction of certain data
     *
     * @param dataId the dataId fetched from the mapper
     * @return return the Prediction of corresponding dataId
     */
    @GetMapping("/prediction/data/{dataId}")
    public List<PredictionDO> listDataPredictions(@PathVariable BigInteger dataId){
        return predictionMapper.listByDataId(dataId);
    }

    /**
     * List the Prediction of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Prediction of corresponding projectId
     */
    @GetMapping("/prediction/project/{projectId}")
    public List<PredictionDO> listProjectPredictions(@PathVariable BigInteger projectId){
        return predictionMapper.listByProjectId(projectId);
    }

    /**
     * Delete all the Prediction in the certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("prediction/project/{projectId}")
    public void deleteProjectPredictions(@PathVariable BigInteger projectId){
        predictionMapper.deleteByProjectId(projectId);
    }

    /**
     * Set the accepting status of a certain prediction
     *
     * @param predictionId the id of prediction
     * @param status the status of whether accepted
     */
    @GetMapping("prediction/{predictionId}/{status}")
    public void setAcceptStatus(@PathVariable BigInteger predictionId, @PathVariable int status) {
        if (status == 0) predictionMapper.setNotAccepted(predictionId);
        else if (status == 1) predictionMapper.setAccepted(predictionId);
    }
}
