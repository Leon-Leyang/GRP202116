package com.grp202116.consumerserver.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grp202116.consumerserver.mapper.*;
import com.grp202116.consumerserver.ml.ModelDriver;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import com.grp202116.consumerserver.util.DataUtils;
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
     * Run the ml Model in the certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @PostMapping("/model/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId, @RequestBody JSONObject kwargs) {
        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDO model = modelMapper.getByProjectId(projectId);
        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(project, model, kwargs);

        for (DataDO data: dataList) {
            JSONObject object = JSONObject.parseObject(restTemplate.postForObject("http://sidecar-server/model/run",
                    HttpUtils.parseJsonToFlask(JSONObject.toJSONString(modelDriver.runModelConfig(data))), String.class));
            JSONArray predictions = JSONObject.parseArray(object.getString("result"));
            predictionMapper.alter();
            predictionMapper.insertAll(modelDriver.savePredictions(predictions));
        }
    }

    /**
     * Train custom model
     * @param projectId id of the specified project
     */
    @PostMapping("/model/train/{projectId}")
    public void trainModel(@PathVariable BigInteger projectId, @RequestBody JSONObject params) {
        List<AnnotationDO> annotationList = annotationMapper.listByProjectId(projectId);
        if (annotationList.size() < 1) return;

        List<DataDO> annotatedDataList = dataMapper.getAnnotatedList();
        if (annotatedDataList.size() < 1) return;

        ProjectDO project = projectMapper.getByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(project, params.getJSONObject("kwargs"));
        JSONObject param = modelDriver.trainModelConfig(params.getString("save_path"));
        param.put("annotation_list", annotationList);
        param.put("data_list", annotatedDataList);

        restTemplate.postForObject("http://sidecar-server/model/train",
                HttpUtils.parseJsonToFlask(JSONObject.toJSONString(param)), String.class);
    }

    @PostMapping("/model/save/{projectId}")
    public void saveModel(@PathVariable BigInteger projectId, @RequestBody ModelDO model) {

        model.setProjectId(projectId);
        model.setCreateTime(new Date());
        modelMapper.insert(model);
    }

    /**
     * Copy the custom model to our address
     */
    @PostMapping("/model/custom/save")
    public void saveCustomModel(@RequestBody String customPath) {
        DataUtils.saveCustom(customPath);
    }
}
