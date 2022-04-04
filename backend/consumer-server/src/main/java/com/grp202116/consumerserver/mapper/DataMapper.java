package com.grp202116.consumerserver.mapper;

import com.grp202116.consumerserver.pojo.DataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * This is the mapper class of the data table,
 * controlling interactions with the database.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see DataDO
 */
@Mapper
public interface DataMapper extends BaseMapper {
    /**
     * Insert all the Data to the database
     *
     * @param dataList the uploaded Data
     */
    void insertAll(List<DataDO> dataList);

    /**
     * Get the Data by the dataId
     *
     * @param id dataId
     * @return return the Data by the dataId
     */
    DataDO getByDataId(BigInteger id);

    /**
     * List all annotated data
     *
     * @return the list of data
     */
    List<DataDO> getAnnotatedList();

    /**
     * List the Data of the certain project by the projectId
     *
     * @param projectId the projectId
     * @return the Data in the corresponding project
     */
    List<DataDO> listByProjectId(BigInteger projectId);

    /**
     * Delete the Data by the projectId
     *
     * @param projectId the projectId
     */
    @Override
    void deleteByProjectId(BigInteger projectId);

    /**
     * Delete the data by its id
     *
     * @param dataId dataId
     */
    void deleteDataById(BigInteger dataId);

    /**
     * Alter annotation table to reset auto-increment from the largest number
     */
    void alter();

    /**
     * Set a certain data to the status of annotated
     *
     * @param dataId the id of data
     */
    void setAnnotated(BigInteger dataId);

    /**
     * Set a certain data to the status of not being annotated
     *
     * @param dataId the id of data
     */
    void setNotAnnotated(BigInteger dataId);

    /**
     * Updated the {@link DataDO#isPredicted()}
     *
     * @param dataDO the data to be updated
     */
    void updateDataPredict(DataDO dataDO);
}
