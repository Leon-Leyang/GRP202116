package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.AnnotationDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface AnnotationMapper extends BaseMapper {

    void insertAll(List<AnnotationDO> annotations);

    List<AnnotationDO> listByDataId(BigInteger dataId);

    void deleteByDataId(BigInteger dataId);

    @Override
    void deleteByProjectId(BigInteger projectId);

    List<AnnotationDO> listByProjectId(BigInteger projectId);
}
