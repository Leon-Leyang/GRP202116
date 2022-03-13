package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.ModelMapper;
import com.grp202116.backend.ml.ModelDriver;
import com.grp202116.backend.pojo.ModelDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * The Class ModelController, control the ml Model of the project
 * Control the Addition, deletion, update and the running of the Model
 */
@RestController
public class ModelController {
    @Resource
    private ModelMapper modelMapper;

    /**
     * Get the Model of certain project
     * @param projectId the projectId fetched from the mapper
     * @return  return the Model of corresponding projectId
     */
    @GetMapping("/models/{projectId}")
    public ModelDO getProjectModel(@PathVariable BigInteger projectId){
        return modelMapper.getByProjectId(projectId);
    }

    /**
     * Update the Model in certain project by first delete the origin Model,
     * then insert the newly imported Model
     * @param projectId the projectId fetched from the mapper
     * @param model the newly imported Model
     */
    @PostMapping("/models/{projectId}")
    public void updateModel(@PathVariable BigInteger projectId, @RequestBody ModelDO model){
        modelMapper.deleteByProjectId(projectId);
        modelMapper.insert(model);
    }

    /**
     * Delete the Model in certain project
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/models/{projectId}")
    public void deleteModel(@PathVariable BigInteger projectId){
        modelMapper.deleteByProjectId(projectId);
    }

    /**
     * Run the ml Model in the certain project
     * @param projectId the projectId fetched from the mapper
     */
    @GetMapping("/models/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId){
        ModelDO model = modelMapper.getByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(model);
        //modelDriver.runModel();
    }
}
