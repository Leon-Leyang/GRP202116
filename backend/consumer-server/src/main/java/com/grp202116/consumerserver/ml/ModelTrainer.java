package com.grp202116.consumerserver.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.PredictionDO;
import com.grp202116.consumerserver.pojo.ProjectDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class ModelTrainer {

    private static ModelDO model;
    private static ProjectDO project;
    private String scriptName;
    private static final String customPath = "../ml/models";

    public ModelTrainer(ProjectDO project, ModelDO model) {
        ModelTrainer.model = model;
        ModelTrainer.project = project;
    }

    public void setScriptName(String scriptPath) {
        this.scriptName = scriptPath;
    }

    public JSONObject trainModelConfig() {
        JSONObject object = new JSONObject();
        object.put("script_type", model.getType());
        object.put("model_path", model.getUrl());
        object.put("model_root", model.getModelRoot());
        object.put("labels_path", model.getLabelsPath());

        JSONObject params = JSON.parseObject(model.getParams());
        if (scriptName != null) params.put("scriptName", scriptName);
        object.put("params", params);

        return object;
    }


}