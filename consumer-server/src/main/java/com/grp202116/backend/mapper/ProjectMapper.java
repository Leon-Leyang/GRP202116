package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * The ProjectMapper define the actions or the operations of the Project
 */
@Mapper
public interface ProjectMapper extends BaseMapper {
    /**
     * Get the Project by id
     * @param id the projectId
     * @return return the Project
     */
    ProjectDO getByProjectId(BigInteger id);

    /**
     * Insert the Project to the database
     * @param project the newly set Project
     */
    void insert(ProjectDO project);

    /**
     * Edit the Project
     * @param project the Project under editing
     */
    void edit(ProjectDO project);

    /**
     * List all the Projects
     * @return all the Projects
     */
    List<ProjectDO> listAll();

    /**
     * Delete the Project by id
     * @param id the projectId
     */
    @Override
    void deleteByProjectId(BigInteger id);

    void alter();
}
