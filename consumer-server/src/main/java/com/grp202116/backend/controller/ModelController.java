package com.grp202116.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.backend.mapper.ModelMapper;
import com.grp202116.backend.mapper.PredictionMapper;
import com.grp202116.backend.ml.ModelDriver;
import com.grp202116.backend.ml.ModelDriver_backup;
import com.grp202116.backend.pojo.ModelDO;
import com.grp202116.backend.pojo.PredictionDO;
import com.grp202116.backend.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class ModelController, control the ml Model of the project
 * Control the Addition, deletion, update and the running of the Model
 */
@RestController
public class ModelController {
    @Resource
    PredictionMapper predictionMapper;

    private final RestTemplate restTemplate;

    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Resource
    private ModelMapper modelMapper;

    /**
     * Get the Model of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Model of corresponding projectId
     */
    @GetMapping("/model/{projectId}")
    public ModelDO getProjectModel(@PathVariable BigInteger projectId) {
        return modelMapper.getByProjectId(projectId);
    }

    /**
     * Update the Model in certain project by first delete the origin Model,
     * then insert the newly imported Model
     *
     * @param projectId the projectId fetched from the mapper
     * @param model     the newly imported Model
     */
    @PostMapping("/model/{projectId}")
    public void updateModel(@PathVariable BigInteger projectId, @RequestBody ModelDO model) {
        modelMapper.deleteByProjectId(projectId);
        modelMapper.insert(model);
    }

    /**
     * Delete the Model in certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/model/{projectId}")
    public void deleteModel(@PathVariable BigInteger projectId) {
        modelMapper.deleteByProjectId(projectId);
    }

    /**
     * Run the ml Model in the certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @GetMapping("/model/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId) {

        System.out.println(projectId);
        ModelDriver modelDriver = new ModelDriver(projectId);
        JSONObject object = JSONObject.parseObject(restTemplate.postForObject("http://sidecar-server/model",
                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(modelDriver.parseConfig())), String.class));
        JSONArray predictions = JSONObject.parseArray(object.getString("result"));

        predictionMapper.insertAll(modelDriver.savePredictions(predictions));
    }
}
