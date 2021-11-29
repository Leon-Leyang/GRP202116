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

@RestController
public class PredictionController {
    @Resource
    private PredictionMapper predictionMapper;

    @GetMapping("/predictions/data/{dataId}")
    public List<PredictionDO> listDataPredictions(@PathVariable BigInteger dataId){
        return predictionMapper.listByDataId(dataId);
    }

    @GetMapping("/predictions/project/{projectId}")
    public List<PredictionDO> listProjectPredictions(@PathVariable BigInteger projectId){
        return predictionMapper.listByProjectId(projectId);
    }

    @DeleteMapping("predictions/project/{projectId}")
    public void deleteProjectPredictions(@PathVariable BigInteger projectId){
        predictionMapper.deleteByProjectId(projectId);
    }
}
