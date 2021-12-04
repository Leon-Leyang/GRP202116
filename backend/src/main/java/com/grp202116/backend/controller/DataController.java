package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.pojo.DataDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@RestController
public class DataController {
    @Resource
    private DataMapper dataMapper;

    @GetMapping("/datas/{dataId}")
    public DataDO getData(@PathVariable BigInteger dataId){
        return dataMapper.getByDataId(dataId);
    }

    @GetMapping("/datas/project/{projectId}")
    public List<DataDO> listProjectDatas(@PathVariable BigInteger projectId){
        return dataMapper.listByProjectId(projectId);
    }

    @PutMapping("/datas")
    public void addDatas(@RequestBody List<DataDO> datas){
        dataMapper.insertAll(datas);
    }

    @DeleteMapping("/datas/project/{projectId}")
    public void deleteProjectDatas(@PathVariable BigInteger projectId){
        dataMapper.deleteByProjectId(projectId);
    }

    @DeleteMapping("/datas/{dataIds}")
    public void deleteDatas(@PathVariable String dataIds){
        dataMapper.deleteDatas(dataIds);
    }
}