package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.AnnotationMapper;
import com.grp202116.backend.pojo.AnnotationDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@RestController
public class AnnotationController {
    @Resource
    private AnnotationMapper annotationMapper;

    @GetMapping("/annotations/data/{dataId}")
    public List<AnnotationDO> listDataAnnotations(@PathVariable BigInteger dataId){
        return annotationMapper.listByDataId(dataId);
    }

    @GetMapping("/annotations/project/{projectId}")
    public List<AnnotationDO> listProjectAnnotations(@PathVariable BigInteger projectId){
        return annotationMapper.listByProjectId(projectId);
    }

    @PutMapping("/annotations/data/{dataId}")
    public void updateDataAnnotations(@PathVariable BigInteger dataId, @RequestBody List<AnnotationDO> annotations){
        annotationMapper.deleteByDataId(dataId);
        annotationMapper.insertAll(annotations);
    }

    @DeleteMapping("/annotations/data/{dataId}")
    public void deleteDataAnnotations(@PathVariable BigInteger dataId){
        annotationMapper.deleteByDataId(dataId);
    }

    @DeleteMapping("/annotations/project/{projectId}")
    public void deleteProjectAnnotations(@PathVariable BigInteger projectId) {
        annotationMapper.deleteByProjectId(projectId);
    }
}
