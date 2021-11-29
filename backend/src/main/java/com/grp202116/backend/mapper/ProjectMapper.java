package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper {

    public ProjectDO getByProjectId(BigInteger id);

    public void insert(ProjectDO project);

    public List<ProjectDO> listAll();

    @Override
    public void deleteByProjectId(BigInteger id);
}
