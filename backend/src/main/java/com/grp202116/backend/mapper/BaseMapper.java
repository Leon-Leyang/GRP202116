package com.grp202116.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface BaseMapper {
    void deleteByProjectId(BigInteger projectId);
}
