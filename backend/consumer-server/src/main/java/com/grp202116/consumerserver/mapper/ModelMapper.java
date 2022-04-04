package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.ModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.maven.model.Model;

import java.math.BigInteger;
import java.util.List;

/**
 * This is the mapper class of the model table,
 * which controls interaction with the database.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see ModelDO
 */
@Mapper
public interface ModelMapper extends BaseMapper {
    /**
     * Insert the Model
     *
     * @param model the uploaded Model
     */
    void insert(ModelDO model);

    /**
     * Get the Model by the projectId
     *
     * @param projectId the projectId
     * @return the Model of the corresponding project
     */
    List<ModelDO> getByProjectId(BigInteger projectId);

    /**
     * Delete the model of the certain project
     *
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * Alter model table to reset auto-increment from the largest number
     */
    void alter();

    /**
     * Get a model based the version and project id
     *
     * @param version   the version
     * @param projectId the id of project
     * @return a {@link ModelDO}
     */
    ModelDO getByVersionProject(@Param("version") String version, @Param("projectId") BigInteger projectId);

    /**
     * Delete a model by a given id
     *
     * @param modelId the id of a model
     */
    void deleteById(BigInteger modelId);

    /**
     * Update a model
     *
     * @param model the model to be updated
     */
    void updateModel(ModelDO model);
}
