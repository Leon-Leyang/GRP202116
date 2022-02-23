package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper {

    ProjectDO getByProjectId(BigInteger id);

    void insert(ProjectDO project);

    void edit(BigInteger id);

    List<ProjectDO> listAll();

    @Override
    void deleteByProjectId(BigInteger id);
}
