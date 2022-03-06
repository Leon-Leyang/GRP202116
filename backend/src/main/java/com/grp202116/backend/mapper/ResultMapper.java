package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.ResultDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ResultMapper{

    void insertAll(List<ResultDO> results);

    //List<ResultDO> listById(BigInteger resultId);

    void deleteByLabelId(BigInteger labelId);

    List<ResultDO> listByLabelId(BigInteger labelId);
}
