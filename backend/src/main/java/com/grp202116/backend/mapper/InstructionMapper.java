package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.InstructionDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface InstructionMapper extends BaseMapper{
    public void insert(InstructionDO instruction);

    @Override
    public void deleteByProjectId(BigInteger projectId);

    public InstructionDO getByProjectId(BigInteger projectId);
}
