package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.AnnotationMapper;
import com.grp202116.consumerserver.mapper.DataMapper;
import com.grp202116.consumerserver.pojo.AnnotationDO;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * The Class AnnotationController controls the Annotations of data
 * Includes selection, insertion and deletion methods.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see AnnotationMapper
 * @see DataMapper
 */
@RestController
public class AnnotationController {
    @Resource
    private AnnotationMapper annotationMapper;
    @Resource
    private DataMapper dataMapper;

    /**
     * List all annotations of the specified data.
     *
     * @param dataId the id of the data being annotated.
     * @return the list of annotations
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
     * Update annotations of the specified data,
     * insert the received {@link AnnotationDO} and updated if there are existing annotations.
     * Then update the data according to the annotation status
     *
     * @param dataId     the id of data
     * @param annotation the annotation to be updated
     * @see DataMapper#setAnnotated(BigInteger)
     * @see AnnotationMapper#insert(AnnotationDO)
     */
    @PutMapping("/annotation/data/{dataId}")
    public void updateDataAnnotations(@PathVariable BigInteger dataId, @RequestBody AnnotationDO annotation) {
        if (annotation == null) return;

        if (annotation.getResult() == null || annotation.getResult().equals("")) {
            dataMapper.setNotAnnotated(dataId);
        } else {
            dataMapper.setAnnotated(dataId);
        }
        DataDO data = dataMapper.getByDataId(dataId);
        Date date = new Date();
        annotation.setUpdateTime(date);
        annotation.setDataId(dataId);
        annotation.setProjectId(data.getProjectId());
        annotationMapper.insert(annotation);
    }

    /**
     * Delete annotations of the specified data
     *
     * @param dataId the id of this data
     */
    @DeleteMapping("/annotation/data/{dataId}")
    public void deleteDataAnnotations(@PathVariable BigInteger dataId) {
        dataMapper.setNotAnnotated(dataId);
        annotationMapper.deleteByDataId(dataId);
    }

    /**
     * Delete annotations of an entire project
     *
     * @param projectId the id of a project
     */
    @DeleteMapping("/annotation/project/{projectId}")
    public void deleteProjectAnnotations(@PathVariable BigInteger projectId) {
        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        for (DataDO data: dataList) dataMapper.setNotAnnotated(data.getDataId());
        annotationMapper.deleteByProjectId(projectId);
    }


}
