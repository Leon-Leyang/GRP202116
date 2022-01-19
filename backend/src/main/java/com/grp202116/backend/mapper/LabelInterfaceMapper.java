package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.LabelInterfaceDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface LabelInterfaceMapper extends BaseMapper {

    void insert(LabelInterfaceDO labelInterface);

    @Override
    void deleteByProjectId(BigInteger projectId);

    LabelInterfaceDO getByProjectId(BigInteger projectId);
}
