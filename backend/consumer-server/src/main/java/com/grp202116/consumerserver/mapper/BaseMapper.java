package com.grp202116.consumerserver.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

/**
 * The BaseMapper which define the other Mappers' basic behaviors
 *
 * @author Yujie Chen
 * @version 1.2
 */
@Mapper
public interface BaseMapper {
    /**
     * The deletion of certain object in the certain project by projectId
     *
     * @param projectId the projectId
     */
    void deleteByProjectId(BigInteger projectId);
}
