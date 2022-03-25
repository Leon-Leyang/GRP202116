package com.grp202116.backend.mapper;

import com.grp202116.backend.pojo.DataDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * The DataMapper define the actions or the operations of the Data
 */
@Mapper
public interface DataMapper extends BaseMapper {
    /**
     * Insert all the Data to the database
     * @param dataList the uploaded Data
     */
    void insertAll(List<DataDO> dataList);

    /**
     * Get the Data by the dataId
     * @param id dataId
     * @return return the Data by the dataId
     */
    DataDO getByDataId(BigInteger id);

    /**
     * List the Data of the certain project by the projectId
     * @param projectId the projectId
     * @return the Data in the corresponding project
     */
    List<DataDO> listByProjectId(BigInteger projectId);

    /**
     * Delete the Data by the projectId
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * Delete the data by its id
     * @param dataId dataId
     */
    void deleteDataById(String dataId);

    Boolean getAnnotateStatus(String dataId);

    void alter();
}
