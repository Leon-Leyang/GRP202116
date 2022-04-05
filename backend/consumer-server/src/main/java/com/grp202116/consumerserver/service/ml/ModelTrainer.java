package com.grp202116.consumerserver.service.ml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.ProjectDO;

/**
 * This ModelTrainer class contains methods related to model training.
 *
 * @author Yujie Chen
 * @version 1.2
 */
public class ModelTrainer {

    private static ModelDO model;
    private static ProjectDO project;
    private String scriptName;

    /**
     * Construct a model trainer based on a project and a model
     *
     * @param project the project {@link ProjectDO}
     * @param model   the model {@link ModelDO}
     */
    public ModelTrainer(ProjectDO project, ModelDO model) {
        ModelTrainer.model = model;
        ModelTrainer.project = project;
    }

    /**
     * Set the script name for a custom script file
     *
     * @param scriptPath the path to the script file
     */
    public void setScriptName(String scriptPath) {
        this.scriptName = scriptPath;
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


}