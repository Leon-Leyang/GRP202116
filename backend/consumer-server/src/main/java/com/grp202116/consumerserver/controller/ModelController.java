package com.grp202116.consumerserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.mapper.*;
import com.grp202116.consumerserver.ml.ModelDriver;
import com.grp202116.consumerserver.ml.ModelSaver;
import com.grp202116.consumerserver.ml.ModelTrainer;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import com.grp202116.consumerserver.util.HttpUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

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

    @Resource
    ModelMapper modelMapper;

    @Resource
    ProjectMapper projectMapper;

    @Resource
    DataMapper dataMapper;

    private final RestTemplate restTemplate;

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
     * Save model and params
     *
     * @param projectId project id
     * @param model     model
     */
    @PostMapping("/model/create/{projectId}")
    public void createModel(@PathVariable BigInteger projectId,
                            @RequestBody ModelDO model) {

        model.setCreateTime(new Date());
        model.setProjectId(projectId);

        ModelSaver modelSaver = new ModelSaver(model.getType());
        String modelPath = modelSaver.saveModel(model.getModelPath());
        if (modelPath == null) return;
        else model.setModelPath(modelPath);

        String labelPath = modelSaver.saveLabels(model.getLabelsPath());
        if (labelPath == null) return;
        else model.setLabelsPath(labelPath);

        JSONObject params = JSON.parseObject(model.getParams());
        String vocPath = params.getString("vocabPath");
        if (vocPath != null) {
            String targetPath = modelSaver.saveLabels(vocPath);
            if (targetPath == null) return;
            else {
                params.put("vocabPath", targetPath);
                model.setParams(JSONObject.toJSONString(params));
            }
        }

        modelMapper.insert(model);
    }

    /**
     * Run the ml Model in the certain project
     * requires the version of model and corresponding params
     * if it is a custom model, requires a .py script file .
     *
     * @param projectId the projectId fetched from the mapper
     */
    @PostMapping("/model/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId,
                         @RequestParam("model_version") String version,
                         @RequestParam("script_url") String scriptPath) {

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersion(version);

        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(project, model);

        String scriptName;
        if (model.getType().equals("Custom")) {
            if (scriptPath == null) return;
            else {
                scriptName = ModelSaver.saveCustom(scriptPath);
                if (scriptName == null) return;
                else modelDriver.setScriptName(scriptName);
            }
        }

        for (DataDO data : dataList) {
            JSONObject object = modelDriver.runModelConfig(data);

            JSONObject result = JSONObject.parseObject(
                    restTemplate.postForObject("http://sidecar-server/model/run",
                            HttpUtils.parseJsonToFlask(JSONObject.toJSONString(object)), String.class));
            JSONArray predictions = JSONObject.parseArray(result.getString("result"));

            data.setPredicted(true);
            dataMapper.updateDataPredict(data);
            predictionMapper.alter();
            predictionMapper.insertAll(modelDriver.savePredictions(predictions));
        }
    }

    /**
     * Train custom model
     *
     * @param projectId id of the specified project
     */
    @PostMapping("/model/train/{projectId}")
    public String trainModel(@PathVariable BigInteger projectId,
                             @RequestParam("model_version") String version,
                             @RequestParam("params") JSONObject trainParams,
                             @RequestParam("script_url") String scriptPath) {

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByVersion(version);

        List<AnnotationDO> annotationList = annotationMapper.listByProjectId(projectId);
        if (annotationList.size() < 1) return "";

        List<DataDO> annotatedDataList = dataMapper.getAnnotatedList();
        if (annotatedDataList.size() < 1) return "";

        ModelTrainer modelTrainer = new ModelTrainer(project, model);

        String scriptName;
        if (model.getType().equals("Custom")) {
            if (scriptPath == null) return null;
            else {
                scriptName = ModelSaver.saveCustom(scriptPath);
                if (scriptName == null) return null;
                else modelTrainer.setScriptName(scriptName);
            }
        }
        JSONObject object = new JSONObject();
        object.putAll(trainParams);
        object.putAll(modelTrainer.trainModelConfig());
        object.put("annotation_list", annotationList);
        object.put("data_list", annotatedDataList);

        return restTemplate.postForObject("http://sidecar-server/model/train",
                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(object)), String.class);

    }

    @Deprecated
    @PostMapping("/model/save/{projectId}")
    public void saveModel(@PathVariable BigInteger projectId, @RequestBody ModelDO model) {

        model.setProjectId(projectId);
        model.setCreateTime(new Date());
        modelMapper.insert(model);
    }
}
