package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.DataMapper;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


//TODO : Create the method for delete the annotation of corresponding annotationId

/**
 * The Class AnnotationController, control the Annotations of certain project data
 * Control the insertion, deletion and update of the Annotations by mapper
 */
@RestController
public class AnnotationController {
    @Resource
    private AnnotationMapper annotationMapper;
    @Resource
    private DataMapper dataMapper;

    /**
     * List the Annotations of certain data
     *
     * @param dataId the dataId fetched from the mapper
     * @return return the Annotations of corresponding dataId
     */
    @GetMapping("/annotation/data/{dataId}")
    public List<AnnotationDO> listDataAnnotations(@PathVariable BigInteger dataId) {
        return annotationMapper.listByDataId(dataId);
    }

    /**
     * List the Annotations of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Annotations of corresponding projectId
     */
    @GetMapping("/annotation/project/{projectId}")
    public List<AnnotationDO> listProjectAnnotations(@PathVariable BigInteger projectId) {
        return annotationMapper.listByProjectId(projectId);
    }

    /**
     * Update the Annotations in certain data
     *
     * @param dataId      the dataId fetched from the mapper
     */
    @PutMapping("/annotation/data/{dataId}")
    public void updateDataAnnotations(@PathVariable BigInteger dataId, @RequestBody AnnotationDO annotation) {

        if (annotation.getResult() == null || annotation.getResult().equals("")) {
            dataMapper.setNotAnnotated(dataId);
        } else {
            Date date = new Date();
            annotation.setUpdateTime(date);
            annotationMapper.update(annotation);
            dataMapper.setAnnotated(dataId);
        }
    }

    /**
     * Delete the Annotations in certain data
     *
     * @param dataId the dataId fetched from the mapper
     */
    @DeleteMapping("/annotation/data/{dataId}")
    public void deleteDataAnnotations(@PathVariable BigInteger dataId) {
        annotationMapper.deleteByDataId(dataId);
    }

    /**
     * Delete the Annotations in certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/annotation/project/{projectId}")
    public void deleteProjectAnnotations(@PathVariable BigInteger projectId) {
        annotationMapper.deleteByProjectId(projectId);
    }


}
