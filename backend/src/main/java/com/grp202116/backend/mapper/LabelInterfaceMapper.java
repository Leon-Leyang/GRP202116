package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.LabelInterfaceDO;
import com.grp202116.backend.pojo.ModelDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface LabelInterfaceMapper extends BaseMapper {

    public void insert(LabelInterfaceDO labelInterface);

    @Override
    public void deleteByProjectId(BigInteger projectId);

    public LabelInterfaceDO getByProjectId(BigInteger projectId);
}
