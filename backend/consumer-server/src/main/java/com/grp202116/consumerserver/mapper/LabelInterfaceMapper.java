package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.LabelInterfaceDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

/**
 * The LabelInterfaceMapper define the actions or the operations of the LabelInterface
 *
 * @author Yujie Chen
 * @version 1.2
 */
@Deprecated
@Mapper
public interface LabelInterfaceMapper extends BaseMapper {
    /**
     * Insert the Label Interface to the database
     *
     * @param labelInterface the set Label Interface
     */
    void insert(LabelInterfaceDO labelInterface);

    /**
     * Delete the Label Interface of the project
     *
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * Get the Label Interface by the projectId
     *
     * @param projectId the projectId
     * @return the Label Interface of the corresponding project
     */
    LabelInterfaceDO getByProjectId(BigInteger projectId);
}
