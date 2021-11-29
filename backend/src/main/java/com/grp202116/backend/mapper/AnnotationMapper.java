package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.AnnotationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface AnnotationMapper extends BaseMapper {

    public void insertAll(List<AnnotationDO> annotations);

    public List<AnnotationDO> listByDataId(BigInteger dataId);

    public void deleteByDataId(BigInteger dataId);

    @Override
    public void deleteByProjectId(BigInteger projectId);

    public List<AnnotationDO> listByProjectId(BigInteger projectId);
}
