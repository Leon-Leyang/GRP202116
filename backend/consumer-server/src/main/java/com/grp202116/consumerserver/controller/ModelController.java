package com.grp202116.consumerserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.ModelMapper;
import com.grp202116.consumerserver.mapper.PredictionMapper;
import com.grp202116.consumerserver.ml.ModelDriver;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * The Class ModelController, control the ml Model of the project
 * Control the Addition, deletion, update and the running of the Model
 */
@RestController
public class ModelController {
    @Resource
    PredictionMapper predictionMapper;

    @Resource
    AnnotationMapper annotationMapper;

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

        ModelDriver modelDriver = new ModelDriver(projectId);
        JSONObject object = JSONObject.parseObject(restTemplate.postForObject("http://sidecar-server/model/run",
                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(modelDriver.parseConfig())), String.class));
        JSONArray predictions = JSONObject.parseArray(object.getString("result"));

        predictionMapper.insertAll(modelDriver.savePredictions(predictions));
    }

    @GetMapping("/model/train/{projectId}")
    public void trainModel(@PathVariable BigInteger projectId) {
        List<AnnotationDO> annotationList = annotationMapper.listByProjectId(projectId);
        //
//        restTemplate.postForObject("http://sidecar-server/model/run",
//                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(modelDriver.parseConfig())), String.class);
    }
}
