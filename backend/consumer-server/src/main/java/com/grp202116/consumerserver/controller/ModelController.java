package com.grp202116.consumerserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.mapper.*;
import com.grp202116.consumerserver.pojo.*;
import com.grp202116.consumerserver.service.ml.ModelDriver;
import com.grp202116.consumerserver.service.ml.ModelSaver;
import com.grp202116.consumerserver.service.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.grp202116.consumerserver.service.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

/**
 * This is the controller which deals with the model,
 * including select, insert and delete models.
 *
 * @author Yujie Chen
 * @version 1.2
 */
@RestController
public class ModelController {
    @Resource
    PredictionMapper predictionMapper;
    @Resource
    AnnotationMapper annotationMapper;
    @Resource
    ModelMapper modelMapper;
    @Resource
    ProjectMapper projectMapper;
    @Resource
    DataMapper dataMapper;

    private static boolean isTesting = false;
    private static boolean isTraining = false;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    /**
     * A constructor to insert a {@link RestTemplate} to interact with the flask server.
     *
     * @param restTemplate insertion of restTemplate
     */
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get the Model of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Model of corresponding projectId
     */
    @GetMapping("/model/{projectId}")
    public List<ModelDO> getProjectModel(@PathVariable BigInteger projectId) {
        return modelMapper.getByProjectId(projectId);
    }

    /**
     * Update the Model in certain project.
     *
     * @param model the newly imported Model
     */
    @PostMapping("/model/update")
    public void updateModel(@RequestBody ModelDO model) {
        ModelSaver modelSaver = new ModelSaver(model);
        if (modelSaver.saveModel()) return;
        modelMapper.updateModel(modelSaver.getModel());
    }

    /**
     * Delete the Model in certain project
     *
     * @param modelId the projectId fetched from the mapper
     */
    @DeleteMapping("/model/{modelId}")
    public void deleteModel(@PathVariable BigInteger modelId) {
        modelMapper.deleteById(modelId);
    }


    /**
     * Create a new model and save it to the database
     *
     * @param projectId project id
     * @param modelList a list of {@link ModelDO} object
     */
    @PostMapping("/model/create/{projectId}")
    public void createModel(@PathVariable BigInteger projectId,
                            @RequestBody List<ModelDO> modelList) {
        for (ModelDO model : modelList) {
            model.setCreateTime(new Date());
            model.setProjectId(projectId);

            ModelSaver modelSaver = new ModelSaver(model);
            if (modelSaver.saveModel()) return;

            modelMapper.insert(modelSaver.getModel());
        }
    }

    /**
     * Run a model based on the inputs
     *
     * @param projectId the id of a project
     * @param runObject the {@link JSONObject} of the params, containing all required information
     */
    @PostMapping("/model/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId, @RequestBody JSONObject runObject) {
        isTesting = true;
        if (!runObject.containsKey("version") || !runObject.containsKey("script_url")) {
            isTesting = false;
            logger.warn("Missing json parameters");
            return;
        }
        String version = runObject.getString("version");
        String scriptPath = runObject.getString("script_url");

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersionProject(version, projectId);

        if (model == null) {
            logger.warn("Model cannot be located according to the project and version");
            isTesting = false;
            return;
        }

        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(project, model);

        if (modelDriver.setScript(scriptPath)) {
            logger.warn("Missing custom script");
            isTesting = false;
            return;
        }

        for (DataDO data : dataList) {
            JSONObject object = modelDriver.runModelConfig(data);
            if (object == null) {
                isTesting = false;
                return;
            }

            JSONObject result = JSONObject.parseObject(
                    restTemplate.postForObject("http://sidecar-server/model/run",
                            HttpUtils.parseJsonToFlask(JSONObject.toJSONString(object)), String.class));
            JSONArray predictions = result.getJSONArray("result");
            if (predictions == null) continue;

            data.setPredicted(true);
            dataMapper.updateDataPredict(data);
            PredictionDO prediction = modelDriver.savePredictions(predictions);
            if (prediction == null) continue;
            predictionMapper.alter();
            predictionMapper.insert(prediction);
        }
        isTesting = false;
    }

    /**
     * Train a model
     *
     * @param projectId   the id of a project
     * @param trainObject the {@link JSONObject} of params
     */
    @PostMapping("/model/train/{projectId}")
    public void trainModel(@PathVariable BigInteger projectId, @RequestBody JSONObject trainObject) {
        isTraining = true;
        if (!trainObject.containsKey("version") && !trainObject.containsKey("params") &&
                !trainObject.containsKey("script_url")) {
            logger.warn("Missing json parameters");
            isTraining = false;
            return;
        }

        String version = trainObject.getString("version");
        String scriptPath = trainObject.getString("script_url");
        JSONObject trainParams = trainObject.getJSONObject("params");

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersionProject(version, projectId);

        List<AnnotationDO> annotationList = annotationMapper.listByProjectId(projectId);

        List<DataDO> annotatedDataList = dataMapper.getAnnotatedList(projectId);
        if (annotatedDataList.size() < 1 || annotationList.size() < 1) {
            isTraining = false;
            return;
        }

        ModelDriver modelDriver = new ModelDriver(project, model);

        if (modelDriver.setScript(scriptPath)) {
            isTraining = false;
            logger.warn("Missing custom script");
            return;
        }

        JSONObject object = new JSONObject();
        object.putAll(modelDriver.trainModelConfig(trainParams));
        object.put("annotation_list", annotationList);
        object.put("data_list", annotatedDataList);

        JSONObject result = JSONObject.parseObject(
                restTemplate.postForObject("http://sidecar-server/model/train",
                        HttpUtils.parseJsonToFlask(JSONObject.toJSONString(object)), String.class));

        model.setAccuracy(result.getString("accuracy"));
        model.setDataLength(result.getInteger("trainNum"));
        modelMapper.updateAccuracy(model);
        isTraining = false;
    }

    /**
     * Get the status of model
     *
     * @param getRun if true, return the status of running, otherwise return the status of training
     * @return status
     */
    @GetMapping("/model/status/{getRun}")
    public boolean getRunModelStatus(@PathVariable boolean getRun) {
        if (getRun) return isTesting;
        return isTraining;
    }
}
