package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.PredictionMapper;
import com.grp202116.backend.pojo.PredictionDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * The Class PredictionController, control the Prediction in the project and data
 * Control the list and the deletion of the Predictions
 */
@RestController
public class PredictionController {
    @Resource
    private PredictionMapper predictionMapper;

    /**
     * List the Prediction of certain data
     * @param dataId the dataId fetched from the mapper
     * @return return the Prediction of corresponding dataId
     */
    @GetMapping("/predictions/data/{dataId}")
    public List<PredictionDO> listDataPredictions(@PathVariable BigInteger dataId){
        return predictionMapper.listByDataId(dataId);
    }

    /**
     * List the Prediction of certain project
     * @param projectId the projectId fetched from the mapper
     * @return return the Prediction of corresponding projectId
     */
    @GetMapping("/predictions/project/{projectId}")
    public List<PredictionDO> listProjectPredictions(@PathVariable BigInteger projectId){
        return predictionMapper.listByProjectId(projectId);
    }

    /**
     * Delete all the Prediction in the certain project
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("predictions/project/{projectId}")
    public void deleteProjectPredictions(@PathVariable BigInteger projectId){
        predictionMapper.deleteByProjectId(projectId);
    }
}
