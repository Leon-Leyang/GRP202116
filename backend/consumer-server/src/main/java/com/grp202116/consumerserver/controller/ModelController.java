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
        String modelPath = modelSaver.saveModel(model.getModelPath());
        if (modelPath != null) model.setModelPath(modelPath);

        String labelPath = modelSaver.saveLabels(model.getLabelsPath());
        if (labelPath != null) model.setLabelsPath(labelPath);

        if (saveModelParams(model, modelSaver)) return;
        modelMapper.updateModel(model);
    }

    /**
     * Save params of model
     *
     * @param model      a {@link ModelDO} object
     * @param modelSaver the {@link ModelSaver} object
     * @return true if no valid target path
     */
    private static boolean saveModelParams(@RequestBody ModelDO model, ModelSaver modelSaver) {
        JSONObject params = JSON.parseObject(model.getParams());
        String vocPath = params.getString("vocabPath");
        if (vocPath != null) {
            String targetPath = modelSaver.saveLabels(vocPath);
            if (targetPath == null) return true;
            else {
                params.put("vocabPath", targetPath);
                model.setParams(JSONObject.toJSONString(params));
            }
        }
        return false;
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
            String modelPath = modelSaver.saveModel(model.getModelPath());
            if (modelPath == null) {
                FileUtils.deleteDirectory(modelSaver.getModelPath());
                return;
            }
            else model.setModelPath(modelPath);

            String labelPath = modelSaver.saveLabels(model.getLabelsPath());
            if (labelPath == null) {
                FileUtils.deleteDirectory(modelSaver.getModelPath());
                return;
            }
            else model.setLabelsPath(labelPath);

            if (saveModelParams(model, modelSaver)) {
                FileUtils.deleteDirectory(modelSaver.getModelPath());
                return;
            }

            modelMapper.insert(model);
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
        if (!runObject.containsKey("version") || !runObject.containsKey("script_url")) return;
        String version = runObject.getString("version");
        String scriptPath = runObject.getString("script_url");

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersionProject(version, projectId);

        if (model == null) {
            logger.warn("Model cannot be located according to the project and version");
            return;
        }

        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(project, model);

        if (modelDriver.setScript(scriptPath)) return;

        for (DataDO data : dataList) {
            JSONObject object = modelDriver.runModelConfig(data);
            if (object == null) return;

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
    }

    /**
     * Train a model
     *
     * @param projectId   the id of a project
     * @param trainObject the {@link JSONObject} of params
     */
    @PostMapping("/model/train/{projectId}")
    public void trainModel(@PathVariable BigInteger projectId, @RequestBody JSONObject trainObject) {

        if (!trainObject.containsKey("version") && !trainObject.containsKey("params") &&
                !trainObject.containsKey("script_url")) return;

        String version = trainObject.getString("version");
        String scriptPath = trainObject.getString("script_url");
        JSONObject trainParams = trainObject.getJSONObject("params");

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersionProject(version, projectId);

        List<AnnotationDO> annotationList = annotationMapper.listByProjectId(projectId);
        if (annotationList.size() < 1) return;

        List<DataDO> annotatedDataList = dataMapper.getAnnotatedList(projectId);
        if (annotatedDataList.size() < 1) return;

        ModelDriver modelDriver = new ModelDriver(project, model);

        if (modelDriver.setScript(scriptPath)) return;

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
    }
}
