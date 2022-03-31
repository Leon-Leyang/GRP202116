package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.ModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.maven.model.Model;

import java.math.BigInteger;

/**
 * The ModelMapper define the actions or the operations of the ml Model
 */
@Mapper
public interface ModelMapper extends BaseMapper {
    /**
     * Insert the Model
     * @param model the uploaded Model
     */
    void insert(ModelDO model);

    /**
     * Get the Model by the projectId
     * @param projectId the projectId
     * @return the Model of the corresponding project
     */
    ModelDO getByProjectId(BigInteger projectId);

    /**
     * Delete the model of the certain project
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    void alter();

    ModelDO getByVersion(String version);

    void updateParams(ModelDO model);

}
