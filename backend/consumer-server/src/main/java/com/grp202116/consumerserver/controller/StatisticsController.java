package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.PredictionMapper;
import com.grp202116.consumerserver.service.statistics.Statistics;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;

/**
 * The Class StatisticsController, control the Statistics of the project
 * Control the list of the Statistics
 *
 * @author Yujie Chen
 * @version 1.2
 * @see Statistics
 */
@RestController
public class StatisticsController {
    @Resource
    private AnnotationMapper annotationMapper;
    @Resource
    private PredictionMapper predictionMapper;

    /**
     * Get the Statistics of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Statistics of corresponding projectId
     */
    @GetMapping("/statistics/{projectId}")
    public Statistics getStatistics(@PathVariable BigInteger projectId) throws IOException {
        return new Statistics().setupStatistics(projectId);
    }

    /**
     * Get data count
     *
     * @param dataId the id of a certain data
     * @param isAnnotation if true then return the number of annotations
     * @return otherwise return predictions
     */
    @GetMapping("/statistics/{dataId}/{isAnnotation}")
    public int getCount(@PathVariable BigInteger dataId, @PathVariable boolean isAnnotation) {
        if (dataId == null) return 0;
        if (isAnnotation) return annotationMapper.countAnnotation(dataId);
        return predictionMapper.countPrediction(dataId);
    }
}
