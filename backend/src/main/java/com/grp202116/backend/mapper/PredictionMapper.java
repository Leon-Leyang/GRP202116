package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.PredictionDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PredictionMapper extends BaseMapper {

    public void insertAll(List<PredictionDO> predictions);

    public List<PredictionDO> listByDataId(BigInteger dataId);

    public List<PredictionDO> listByProjectId(BigInteger projectId);

    @Override
    public void deleteByProjectId(BigInteger projectId);
}
