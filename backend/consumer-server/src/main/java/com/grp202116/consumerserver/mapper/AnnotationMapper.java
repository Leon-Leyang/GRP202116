package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.AnnotationDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * This is the mapper class of the annotation table,
 * controlling interactions with the database.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see BaseMapper
 * @see AnnotationDO
 */
@Mapper
public interface AnnotationMapper extends BaseMapper {
    /**
     * Insert all the Annotations
     *
     * @param annotations the submitted Annotations
     */
    void insertAll(List<AnnotationDO> annotations);

    /**
     * Insert a specific annotation.
     *
     * @param annotation the annotation to be inserted
     */
    void insert(AnnotationDO annotation);

    /**
     * List all the Annotations in certain data by the dataId
     *
     * @param dataId the dataId
     * @return the Annotations in data by corresponding id
     */
    List<AnnotationDO> listByDataId(BigInteger dataId);

    /**
     * Delete the Annotation in certain data by the dataId
     *
     * @param dataId the dataId
     */
    void deleteByDataId(BigInteger dataId);

    /**
     * Delete the Annotation in certain project by the projectId
     *
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * List all the Annotations in certain project by the projectId
     *
     * @param projectId the projectId
     * @return the Annotations in the project by corresponding id
     */
    List<AnnotationDO> listByProjectId(BigInteger projectId);

    /**
     * Alter annotation table to reset auto-increment from the largest number
     */
    void alter();

    /**
     * Update a certain annotation
     *
     * @param annotationDO the annotation to be updated
     */
    @Deprecated
    void update(AnnotationDO annotationDO);

    int countAnnotation(BigInteger dataId);
}
