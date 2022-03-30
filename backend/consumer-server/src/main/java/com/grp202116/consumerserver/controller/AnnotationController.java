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
     * Update the Annotations in certain data by first delete the origin Annotations,
     * then insert all the new Annotations
     *
     * @param dataId      the dataId fetched from the mapper
     * @param annotations the newly set Annotations
     */
    @PutMapping("/annotation/data/{dataId}")
    public void updateDataAnnotations(@PathVariable BigInteger dataId, @RequestBody List<AnnotationDO> annotations) {
        if (annotations == null) return;

        annotationMapper.deleteByDataId(dataId);
        BigInteger projectId = BigInteger.valueOf(dataMapper.getProjectId(dataId));

        if (annotations.size() != 0) {
            Date date = new Date();
            for (AnnotationDO annotation : annotations) {
                if (annotation.getCreateTime() == null) annotation.setCreateTime(date);
                annotation.setUpdateTime(date);
                annotation.setProjectId(projectId);
            }
            dataMapper.setAnnotated(dataId);
            annotationMapper.alter();
            annotationMapper.insertAll(annotations);
        } else dataMapper.setNotAnnotated(dataId);
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
