package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.*;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import com.grp202116.consumerserver.util.ExportUtils;
import com.grp202116.consumerserver.util.FileUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The Class ProjectController, contain the operation for the Project
 * including list the Project, edit the Project, add and delete of the Project
 */
@RestController
public class ProjectController {
    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private DataMapper dataMapper;

    @Resource
    private AnnotationMapper annotationMapper;

    @Resource
    private PredictionMapper predictionMapper;

    /**
     * Get the Project by the id
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Project of corresponding projectId
     */
    @GetMapping("/project/{projectId}")
    public ProjectDO getProject(@PathVariable BigInteger projectId) {
        return projectMapper.getByProjectId((projectId));
    }

    /**
     * List all the Project
     *
     * @return return all the created Project
     */
    @GetMapping("/project/list")
    public List<ProjectDO> listAllProjects() {
        return projectMapper.listAll();
    }

    /**
     * Get the process of a certain project
     *
     * @return the process, retains four decimal
     */
    @GetMapping("/project/{projectId}/process")
    public String getProjectProcess(@PathVariable BigInteger projectId) {
        List<DataDO> dataList = dataMapper.listByProjectId(projectId);
        int annotatedCount = 0;

        for (DataDO data : dataList) {
            if (data.isAnnotated()) annotatedCount++;
        }

        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format((float) annotatedCount / dataList.size());
    }

    /**
     * Edit the Project information, including edit
     * the name, the type, and the description of the Project
     *
     * @param project the projectId fetched from the mapper
     */
    @PostMapping("/project/edit")
    public void editProject(@RequestBody ProjectDO project) {
        project.setUpdateTime(new Date());
        projectMapper.edit(project);
    }

    /**
     * Add the Project to the database, set the created time and update time
     *
     * @param project the newly set Project
     */
    @PostMapping("/project/add")
    public void addProject(@RequestBody ProjectDO project) {
        Date date = new Date();
        project.setCreateTime(date);
        project.setUpdateTime(date);

        projectMapper.alter();
        projectMapper.insert(project);
    }

    /**
     * Delete the Project by the id
     *
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/project/{projectId}")
    public void deleteProject(@PathVariable BigInteger projectId) {
        String projectDirectory = ".." + File.separator + "files" + File.separator + projectId;
        FileUtils.deleteDirectory(projectDirectory);
        List<ModelDO> modelList = modelMapper.getByProjectId(projectId);

        for (ModelDO model: modelList) {
            String modelDirectory = "../ml/models" + File.separator + model.getProjectId() +
                    "_" + model.getVersion();
            FileUtils.deleteDirectory(modelDirectory);
        }

        projectMapper.deleteByProjectId(projectId);
    }

    /**
     * Export project annotations
     *
     * @param format    csv, tsv or json
     * @param projectId project id
     */
    @PostMapping("/project/{projectId}/data_export/annotations")
    public ResponseEntity<ByteArrayResource> exportAnnotation(@RequestParam("format") String format,
                                                              @PathVariable BigInteger projectId) {
        ByteArrayResource resource = ExportUtils.exportFile(true, format,
                Collections.singletonList(annotationMapper.listByProjectId(projectId)));

        return getResponse(resource);
    }

    /**
     * Export project predictions
     *
     * @param format    csv, tsv or json
     * @param projectId project id
     */
    @PostMapping("/project/{projectId}/data_export/predictions")
    public ResponseEntity<ByteArrayResource> exportPrediction(@RequestParam("format") String format,
                                                              @PathVariable BigInteger projectId) {

        ByteArrayResource resource = ExportUtils.exportFile(false, format,
                Collections.singletonList(predictionMapper.listByProjectId(projectId)));

        return getResponse(resource);
    }

    /**
     * Helper method for the response entity creation
     * @param resource byte source of a certain file
     * @return response of success or failure
     */
    private ResponseEntity<ByteArrayResource> getResponse(ByteArrayResource resource) {
        if (resource == null) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="
                        + ExportUtils.getTargetFile().getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(ExportUtils.getTargetFile().length())
                .body(resource);
    }
}
