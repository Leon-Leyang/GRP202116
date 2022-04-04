package com.grp202116.consumerserver.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
     * Set the script name of a custom python file
     *
     * @param scriptPath the url of the input file
     */
    public void setScriptName(String scriptPath) {
        this.scriptName = scriptPath;
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
     * Convert resulting predictions to a json array
     *
     * @param predictions the {@link JSONArray} of predictions
     * @return a list of {@link ProjectDO}
     */
    public List<PredictionDO> savePredictions(JSONArray predictions) {

        List<PredictionDO> predictionList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(predictions).size(); i++) {
            JSONObject predictionJSONObject = predictions.getJSONObject(i);
            PredictionDO prediction = new PredictionDO();
            prediction.setPredictionId(predictionJSONObject.getString("id"));
            prediction.setType(predictionJSONObject.getString("type"));
            prediction.setResult("[" + predictionJSONObject.toJSONString() + "]");

            Date date = new Date();
            prediction.setCreateTime(date);
            prediction.setUpdateTime(date);
            prediction.setDataId(data.getDataId());
            prediction.setModel(model.getVersion());
            prediction.setProjectId(project.getProjectId());

            predictionList.add(prediction);
        }

        return predictionList;
    }
}
