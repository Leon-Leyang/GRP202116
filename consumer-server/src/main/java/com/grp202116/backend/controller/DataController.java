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
    @GetMapping("/datas/{dataId}")
    public DataDO getData(@PathVariable BigInteger dataId){
        return dataMapper.getByDataId(dataId);
    }

    /**
     *
     * List the Data of certain project
     * @param projectId the projectId fetched from the mapper
     * @return return the Data of corresponding projectId
     */
    @GetMapping("/datas/project/{projectId}")
    public List<DataDO> listProjectDatas(@PathVariable BigInteger projectId){
        return dataMapper.listByProjectId(projectId);
    }

    /**
     * Add the Data to the database
     * @param datas the uploaded data
     */
    @PutMapping("/datas")
    public void addDatas(@RequestBody List<DataDO> datas){
        dataMapper.insertAll(datas);
    }

    /**
     * Delete the Data in certain project
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/datas/project/{projectId}")
    public void deleteProjectDatas(@PathVariable BigInteger projectId){
        dataMapper.deleteByProjectId(projectId);
    }

    /**
     * Delete the Data by the corresponding id
     * @param dataIds the dataId fetched from the mapper
     */
    @DeleteMapping("/datas/{dataIds}")
    public void deleteDatas(@PathVariable String dataIds){
        dataMapper.deleteDatas(dataIds);
    }
}
