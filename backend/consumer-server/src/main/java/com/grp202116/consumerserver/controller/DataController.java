package com.grp202116.consumerserver.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.grp202116.consumerserver.mapper.DataMapper;
import com.grp202116.consumerserver.mapper.ProjectMapper;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import com.grp202116.consumerserver.service.util.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller of data,
 * includes selection, insertion and deletion methods.
 *
 * @author Yujie Chen
 * @version 1.2
 * @see DataMapper
 * @see ProjectMapper
 */
@RestController
public class DataController {
    @Resource
    private DataMapper dataMapper;
    @Resource
    private ProjectMapper projectMapper;

    /**
     * Get the Data by the corresponding id
     *
     * @param dataId the dataId fetched from the mapper
     * @return return the Data of corresponding id
     */
    @GetMapping("/data/{dataId}")
    public DataDO getData(@PathVariable BigInteger dataId) {
        return dataMapper.getByDataId(dataId);
    }

    /**
     * List the Data of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Data of corresponding projectId
     */
    @GetMapping("/data/project/{projectId}")
    public List<DataDO> listProjectDataList(@PathVariable BigInteger projectId) {
        return dataMapper.listByProjectId(projectId);
    }

    /**
     * List the Data of a project by corresponding page number and page size
     *
     * @param pageNum   the number of a page
     * @param pageSize  the size of a page
     * @param projectId the id of required project
     * @return a list of {@link DataDO} in the format of {@link PageInfo}
     */
    @GetMapping("/data/page/{projectId}/{pageNum}/{pageSize}")
    @ResponseBody
    public PageInfo<DataDO> listProjectDataPage(@PathVariable int pageNum, @PathVariable int pageSize,
                                                @PathVariable BigInteger projectId) {

        PageHelper.startPage(pageNum, pageSize);
        List<DataDO> dataList = dataMapper.listByProjectId(projectId);

        return new PageInfo<>(dataList);
    }

    /**
     * Add the Data to the database
     *
     * @param dataList the uploaded data
     * @deprecated
     */
    @Deprecated
    @PutMapping("/data/add")
    public void addDataList(@RequestBody List<DataDO> dataList) {
        dataMapper.insertAll(dataList);
    }

    /**
     * Upload data to the project according to the url,
     * use the {@link FileUtils#uploadProjectData(List, BigInteger, String)}
     * to move files to another local position,
     * then insert the new address by {@link DataMapper#insertAll(List)}
     *
     * @param urlList   the list of data url
     * @param projectId the id of the corresponding project
     */
    @PostMapping("/project/{projectId}/data_url")
    public void uploadDataURL(@RequestBody List<String> urlList, @PathVariable BigInteger projectId) {
        ProjectDO project = projectMapper.getByProjectId(projectId);
        List<File> fileList = new ArrayList<>();
        for (String url : urlList) fileList.add(new File(url));

        List<DataDO> dataList = FileUtils.uploadProjectData(fileList, projectId, project.getType());
        if (dataList != null && dataList.size() != 0) {
            dataMapper.alter();
            dataMapper.insertAll(dataList);
        }
        updateDataNumber(projectId);
    }

    /**
     * Upload data to the project by directly transfer the files.
     * The files have to be convert from {@link MultipartFile} to {@link File}.
     *
     * @param multiFileList the list of files
     * @param projectId     the id of the corresponding project
     */
    @PostMapping("/project/{projectId}/data_file")
    public void uploadDataFile(@RequestParam("fileList") MultipartFile[] multiFileList,
                               @PathVariable BigInteger projectId) {
        ProjectDO project = projectMapper.getByProjectId(projectId);
        List<File> fileList = FileUtils.multipartToFile(multiFileList);
        List<DataDO> dataList = FileUtils.uploadProjectData(fileList, projectId, project.getType());
        if (dataList != null && dataList.size() != 0) {
            dataMapper.alter();
            dataMapper.insertAll(dataList);
        }
        updateDataNumber(projectId);
    }

    /**
     * Delete the Data in certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/data/project/{projectId}")
    public void deleteProjectData(@PathVariable BigInteger projectId) {
        dataMapper.deleteByProjectId(projectId);
    }

    /**
     * Delete the Data by the corresponding id
     *
     * @param dataId    the dataId fetched from the mapper
     * @param projectId the id of a project
     */
    @DeleteMapping("/data/{projectId}/{dataId}")
    public void deleteDataById(@PathVariable BigInteger projectId, @PathVariable BigInteger dataId) {
        dataMapper.deleteDataById(dataId);
        updateDataNumber(projectId);
    }

    /**
     * A helper method for updating the list number of data inside a project
     *
     * @param projectId the id of a project
     */
    private void updateDataNumber(BigInteger projectId) {
        List<DataDO> currentList = dataMapper.listByProjectId(projectId);
        if (currentList.size() < 1) return;
        int number = 1;
        for (DataDO data : currentList) data.setListNumber(number++);
        dataMapper.updateListNumberAll(currentList);
    }
}
