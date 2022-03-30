package com.grp202116.consumerserver.ml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.mapper.ProjectMapper;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.PredictionDO;
import com.grp202116.consumerserver.pojo.ProjectDO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//Todo: combine with the frontend to retrieve requested project id and data id;
public class ModelDriver {

    static ModelDO model;
    static ProjectDO project;
    static DataDO data;

    public ModelDriver(ProjectDO project, ModelDO model, DataDO data) {
        ModelDriver.model = model;
        ModelDriver.project = project;
        ModelDriver.data = data;
    }

    public JSONObject parseConfig() {
//        String configs = "<View>" +
//                "  <Image name=\" image \" value=\" $image \" zoom=\" true \"/>" +
//                " <BrushLabels name=\" tag \" toName=\" image \">" +
//                "     <Label value=\" Airplane \" background=\" rgba(255, 0, 0, 0.7) \"/>" +
//                "                               <Label value=\" Car \" background=\" rgba(0, 0, 255, 0.7) \"/>" +
//                "                           </BrushLabels>" +
//                "                       </View>";
//
//        String labels = "Background, Aeroplane, Bicycle, Bird, Boat, Bottle, Bus, Car, Cat, Chair, Cow," +
//                "Dining table, Dog, Horse, Motorbike, Person, Potted plant, Sheep, Sofa, Train," +
//                " Tv/monitor";
//
//        JSONObject param = new JSONObject();
//
//        param.put("project_type", "Semantic Segmentation Mask");
//        param.put("data", "./puppy.webp");
//        param.put("configs", configs);
//        param.put("model_path", "../ml/models/fcn/fcn.pth");
//        param.put("model_version", "undefined");
//        param.put("model_root", "./");
//        param.put("labels", labels);
        JSONObject param = new JSONObject();
        param.put("project_type", project.getType());
        param.put("data", data.getUrl());
        param.put("configs", project.getConfigs());
        param.put("model_path", model.getUrl());
        param.put("model_version", model.getVersion());
        param.put("model_root", model.getModelRoot());
        param.put("labels", project);

        return param;
    }

    public List<PredictionDO> savePredictions(JSONArray predictions) {
        List<PredictionDO> predictionList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(predictions).size(); i++) {
            JSONObject predictionJSONObject = predictions.getJSONObject(i);
            PredictionDO prediction = new PredictionDO();
            prediction.setPredictionId(predictionJSONObject.getString("id"));
            prediction.setType(predictionJSONObject.getString("type"));
            prediction.setResult(predictionJSONObject.getString("value"));
//            prediction.setFromName(predictionJSONObject.getString("from_name"));
//            prediction.setToName(predictionJSONObject.getString("to_name"));

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
