package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.DataDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface DataMapper extends BaseMapper {

    void insertAll(List<DataDO> datas);

    DataDO getByDataId(BigInteger id);

    List<DataDO> listByProjectId(BigInteger projectId);

    @Override
    void deleteByProjectId(BigInteger projectId);

    void deleteDatas(String dataIds);
}
