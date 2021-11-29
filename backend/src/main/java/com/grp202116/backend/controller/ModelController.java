package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.ModelMapper;
import com.grp202116.backend.ml.ModelDriver;
import com.grp202116.backend.pojo.ModelDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
public class ModelController {
    @Resource
    private ModelMapper modelMapper;

    @GetMapping("/models/{projectId}")
    public ModelDO getProjectModel(@PathVariable BigInteger projectId){
        return modelMapper.getByProjectId(projectId);
    }

    @PostMapping("/models/{projectId}")
    public void updateModel(@PathVariable BigInteger projectId, @RequestBody ModelDO model){
        modelMapper.deleteByProjectId(projectId);
        modelMapper.insert(model);
    }

    @DeleteMapping("/models/{projectId}")
    public void deleteModel(@PathVariable BigInteger projectId){
        modelMapper.deleteByProjectId(projectId);
    }

    @GetMapping("/models/run/{projectId}")
    public void runModel(@PathVariable BigInteger projectId){
        ModelDO model = modelMapper.getByProjectId(projectId);
        ModelDriver modelDriver = new ModelDriver(model);
        modelDriver.run();
    }
}
