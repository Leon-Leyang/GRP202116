package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.PredictionDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface PredictionMapper extends BaseMapper {

    void insertAll(List<PredictionDO> predictions);

    List<PredictionDO> listByDataId(BigInteger dataId);

    List<PredictionDO> listByProjectId(BigInteger projectId);

    @Override
    void deleteByProjectId(BigInteger projectId);
}
