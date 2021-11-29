package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.DataDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface DataMapper extends BaseMapper {

    public void insertAll(List<DataDO> datas);

    public DataDO getByDataId(BigInteger id);

    public List<DataDO> listByProjectId(BigInteger projectId);

    @Override
    public void deleteByProjectId(BigInteger projectId);
}
