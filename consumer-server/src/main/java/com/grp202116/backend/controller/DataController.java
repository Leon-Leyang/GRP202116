package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.pojo.DataDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * The Class DataController, contains the operations for Data
 * including list the Data, add the Data and delete the Data by mapper
 */

@RestController
public class DataController {
    @Resource
    private DataMapper dataMapper;

    /**
     * Get the Data by the corresponding id
     * @param dataId the dataId fetched from the mapper
     * @return return the Data of corresponding id
     */
    @GetMapping("/data/{dataId}")
    public DataDO getData(@PathVariable BigInteger dataId){
        return dataMapper.getByDataId(dataId);
    }

    /**
     *
     * List the Data of certain project
     * @param projectId the projectId fetched from the mapper
     * @return return the Data of corresponding projectId
     */
    @GetMapping("/data/project/{projectId}")
    public List<DataDO> listProjectDataList(@PathVariable BigInteger projectId){
        return dataMapper.listByProjectId(projectId);
    }

    /**
     * Add the Data to the database
     * @param dataList the uploaded data
     */
    @PutMapping("/data/add")
    public void addDataList(@RequestBody List<DataDO> dataList){
        dataMapper.insertAll(dataList);
    }

    /**
     * Delete the Data in certain project
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/data/project/{projectId}")
    public void deleteProjectData(@PathVariable BigInteger projectId){
        dataMapper.deleteByProjectId(projectId);
    }

    /**
     * Delete the Data by the corresponding id
     * @param dataId the dataId fetched from the mapper
     */
    @DeleteMapping("/data/{dataId}")
    public void deleteDataById(@PathVariable String dataId){
        dataMapper.deleteDataById(dataId);
    }
}
