package com.grp202116.consumerserver.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ModelDriver {

    private static ModelDO model;
    private static ProjectDO project;
    private DataDO data;
    private String scriptName;
    private static final String customPath = "../ml/models";

    public ModelDriver(ProjectDO project, ModelDO model) {
        ModelDriver.model = model;
        ModelDriver.project = project;
    }

    public void setScriptName(String scriptPath) {
        this.scriptName = scriptPath;
    }

    public JSONObject runModelConfig(DataDO data) {

        this.data = data;
        JSONObject object = new JSONObject();
        object.put("script_type", model.getType());
        object.put("model_path", model.getModelPath());
        object.put("model_root", model.getModelRoot());
        object.put("labels_path", model.getLabelsPath());
        object.put("data", data.getUrl());
        if (project.getConfigs() == null) {
            System.out.println("There is no configs in the project.");
            return null;
        }
        object.put("configs", project.getConfigs());
        object.put("model_version", model.getVersion());

        JSONObject params = JSON.parseObject(model.getParams());
        if (scriptName != null) params.put("scriptName", scriptName);
        object.put("params", params);

        return object;
    }

    public List<PredictionDO> savePredictions(JSONArray predictions) {
        System.out.println(predictions);
        List<PredictionDO> predictionList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(predictions).size(); i++) {
            JSONObject predictionJSONObject = predictions.getJSONObject(i);
            PredictionDO prediction = new PredictionDO();
            prediction.setPredictionId(predictionJSONObject.getString("id"));
            prediction.setType(predictionJSONObject.getString("type"));
            prediction.setResult(predictionJSONObject.getString("value"));

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
