package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.ProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * This the mapper class of the project table,
 * which controls interactions with the database.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see ProjectDO
 */
@Mapper
public interface ProjectMapper extends BaseMapper {
    /**
     * Get the Project by id
     *
     * @param id the projectId
     * @return return the Project
     */
    ProjectDO getByProjectId(BigInteger id);

    /**
     * Insert the Project to the database
     *
     * @param project the newly set Project
     */
    void insert(ProjectDO project);

    /**
     * Edit the Project
     *
     * @param project the Project under editing
     */
    void edit(ProjectDO project);

    /**
     * List all the Projects
     *
     * @return all the Projects
     */
    List<ProjectDO> listAll();

    /**
     * Delete the Project by id
     *
     * @param id the projectId
     */
    @Override
    void deleteByProjectId(BigInteger id);

    /**
     * Alter project table to reset auto-increment from the largest number
     */
    void alter();
}
