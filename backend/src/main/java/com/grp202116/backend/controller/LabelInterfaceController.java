package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.LabelInterfaceMapper;
import com.grp202116.backend.pojo.LabelInterfaceDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LabelInterfaceController {
    public static final List<Integer> templatesProjectId = null;
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

    @GetMapping("/labelinterfaces/templates")
    public List<LabelInterfaceDO> getTemplates() {
        return null;
    }
}
