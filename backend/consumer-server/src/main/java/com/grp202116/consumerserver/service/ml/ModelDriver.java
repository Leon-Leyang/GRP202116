package com.grp202116.consumerserver.service.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This ModelDriver class contains methods related to ml model running
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ModelDriver {

    private static ModelDO model;
    private static ProjectDO project;
    private DataDO data;
    private String scriptName;

    private static final Logger logger = LoggerFactory.getLogger(ModelDriver.class);

    /**
     * Construct a model driver based on a project and a model
     *
     * @param project the {@link ProjectDO} object
     * @param model   the {@link ModelDO} object
     */
    public ModelDriver(ProjectDO project, ModelDO model) {
        ModelDriver.model = model;
        ModelDriver.project = project;
    }

    /**
     * Save custom model script
     *
     * @param scriptPath the path of this script
     * @return false if success true otherwise
     */
    public boolean setScript(String scriptPath) {
        String scriptName;
        if (model.getType().equals("Customization")) {
            if (scriptPath == null) {
                logger.warn("Script Path not specified for custom model");
                return true;
            } else {
                scriptName = ModelSaver.saveCustom(scriptPath);
                if (scriptName == null) return true;
                else this.scriptName = scriptName;
            }
        }
        return false;
    }

    /**
     * Create a {@link JSONObject} which contains all information of running a ml model
     *
     * @param data the {@link DataDO}
     * @return a {@link JSONObject} object
     */
    public JSONObject runModelConfig(DataDO data) {

        this.data = data;
        JSONObject object = new JSONObject();
        object.put("script_type", model.getType());
        object.put("model_path", model.getModelPath());
        object.put("model_root", model.getModelRoot());
        object.put("labels_path", model.getLabelsPath());
        object.put("data", data.getUrl());
        if (project.getConfigs() == null) {
            logger.warn("There is no config in the project.");
            return null;
        }
        object.put("configs", project.getConfigs());
        object.put("model_version", model.getVersion());

        JSONObject params = JSON.parseObject(model.getParams());
        if (scriptName != null) params.put("scriptName", scriptName);
        object.put("params", params);

        return object;
    }

    /**
     * Create a {@link JSONObject} which contains all needed params for training
     *
     * @return the jsonObject {@link JSONObject}
     */
    public JSONObject trainModelConfig(JSONObject trainParams) {
        JSONObject object = new JSONObject();
        object.put("script_type", model.getType());
        object.put("model_path", model.getModelPath());
        object.put("model_root", model.getModelRoot());
        object.put("labels_path", model.getLabelsPath());

        JSONObject params = JSON.parseObject(model.getParams());
        if (scriptName != null) params.put("scriptName", scriptName);
        params.putAll(trainParams);
        object.put("params", params);

        return object;
    }

    /**
     * Convert resulting predictions to a json array
     *
     * @param predictions the {@link JSONArray} of predictions
     * @return a list of {@link ProjectDO}
     */
    public PredictionDO savePredictions(JSONArray predictions) {
        if (predictions.size() < 1) return null;

        JSONObject predictionJSONObject = predictions.getJSONObject(0);
        PredictionDO prediction = new PredictionDO();
        prediction.setType(predictionJSONObject.getString("type"));
        prediction.setResult("[" + predictions.toJSONString() + "]");

        Date date = new Date();
        prediction.setCreateTime(date);
        prediction.setUpdateTime(date);
        prediction.setDataId(data.getDataId());
        prediction.setModel(model.getVersion());
        prediction.setProjectId(project.getProjectId());

        return prediction;
    }
}
