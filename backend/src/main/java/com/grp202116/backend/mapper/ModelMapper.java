package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.ModelDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface ModelMapper extends BaseMapper {

    void insert(ModelDO model);

    ModelDO getByProjectId(BigInteger projectId);

    @Override
    void deleteByProjectId(BigInteger projectId);
}
