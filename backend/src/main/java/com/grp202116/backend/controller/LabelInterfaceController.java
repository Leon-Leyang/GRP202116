package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.LabelInterfaceMapper;
import com.grp202116.backend.pojo.LabelInterfaceDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
public class LabelInterfaceController {
    @Resource
    private LabelInterfaceMapper labelInterfaceMapper;

    @GetMapping("/labelinterfaces/{projectId}")
    public void getLabelInterface(@PathVariable BigInteger projectId){
        labelInterfaceMapper.getByProjectId(projectId);
    }

    @PostMapping("/labelinterfaces/{projectId}")
    public void updateLabelInterface(@PathVariable BigInteger projectId, @RequestBody LabelInterfaceDO labelInterface){
        labelInterfaceMapper.deleteByProjectId(projectId);
        labelInterfaceMapper.insert(labelInterface);
    }
}
