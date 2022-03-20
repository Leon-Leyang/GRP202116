package com.grp202116.backend.ml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.backend.mapper.PredictionMapper;
import com.grp202116.backend.pojo.ModelDO;
import com.grp202116.backend.pojo.PredictionDO;
import com.grp202116.backend.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//Todo: combine with the frontend to retrieve requested project id and data id;
public class ModelDriver {

    ModelDO model;

    public ModelDriver(BigInteger projectId) {
        this.model = new ModelDO();
    }

    public JSONObject parseConfig() {
        String configs = "<View>" +
                "  <Image name=\" image \" value=\" $image \" zoom=\" true \"/>" +
                " <BrushLabels name=\" tag \" toName=\" image \">" +
                "     <Label value=\" Airplane \" background=\" rgba(255, 0, 0, 0.7) \"/>" +
                "                               <Label value=\" Car \" background=\" rgba(0, 0, 255, 0.7) \"/>" +
                "                           </BrushLabels>" +
                "                       </View>";

        String labels = "Background, Aeroplane, Bicycle, Bird, Boat, Bottle, Bus, Car, Cat, Chair, Cow," +
                "Dining table, Dog, Horse, Motorbike, Person, Potted plant, Sheep, Sofa, Train," +
                " Tv/monitor";

        JSONObject param = new JSONObject();

        param.put("project_type", "Semantic Segmentation Mask");
        param.put("data", "./puppy.webp");
        param.put("configs", configs);
        param.put("model_path", "../ml/models/fcn/fcn.pth");
        param.put("model_version", "undefined");
        param.put("model_root", "./");
        param.put("labels", labels);

        return param;
    }

    public List<PredictionDO> savePredictions(JSONArray predictions) {
        List<PredictionDO> predictionList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(predictions).size(); i++) {
            JSONObject predictionJSONObject = predictions.getJSONObject(i);
            PredictionDO prediction = new PredictionDO();
            prediction.setPredictionId(predictionJSONObject.getString("id"));
            prediction.setType(predictionJSONObject.getString("type"));
            prediction.setValue(predictionJSONObject.getString("value"));
            prediction.setFromName(predictionJSONObject.getString("from_name"));
            prediction.setToName(predictionJSONObject.getString("to_name"));

            Date date = new Date();
            prediction.setCreateTime(date);
            prediction.setUpdateTime(date);
            prediction.setDataId(new BigInteger(String.valueOf(1)));
            prediction.setProjectId(new BigInteger(String.valueOf(1)));

            predictionList.add(prediction);
        }

        return predictionList;
    }
}
