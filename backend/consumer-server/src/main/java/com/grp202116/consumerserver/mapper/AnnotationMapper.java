package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.AnnotationDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * The AnnotationMapper define the actions or the operations of the Annotation
 */
@Mapper
public interface AnnotationMapper extends BaseMapper {
    /**
     * Insert all the Annotations
     * @param annotations the submitted Annotations
     */
    void insertAll(List<AnnotationDO> annotations);

    void insert(AnnotationDO annotation);

    /**
     * List all the Annotations in certain data by the dataId
     * @param dataId the dataId
     * @return the Annotations in data by corresponding id
     */
    List<AnnotationDO> listByDataId(BigInteger dataId);

    /**
     * Delete the Annotation in certain data by the dataId
     * @param dataId the dataId
     */
    void deleteByDataId(BigInteger dataId);

    /**
     * Delete the Annotation in certain project by the projectId
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * List all the Annotations in certain project by the projectId
     * @param projectId the projectId
     * @return the Annotations in the project by corresponding id
     */
    List<AnnotationDO> listByProjectId(BigInteger projectId);

    void alter();

    void update(AnnotationDO annotationDO);
}
