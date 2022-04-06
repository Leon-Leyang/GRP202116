package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.*;
import com.grp202116.consumerserver.pojo.DataDO;
import com.grp202116.consumerserver.pojo.ModelDO;
import com.grp202116.consumerserver.pojo.ProjectDO;
import com.grp202116.consumerserver.service.util.ExportUtils;
import com.grp202116.consumerserver.service.util.FileUtils;
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
 * as well as methods for exporting data
 *
 * @author Yujie Chen
 * @version 1.2
 * @see ProjectMapper
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
     * @param projectId the id of a project
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
        String projectDirectory = ".." + File.separator + "frontend" + File.separator +
                "public" + File.separator + "files" + File.separator + projectId + File.separator;
        FileUtils.deleteDirectory(projectDirectory);
        List<ModelDO> modelList = modelMapper.getByProjectId(projectId);

        for (ModelDO model : modelList) {
            String modelDirectory = ".." + File.separator + "ml" + File.separator +
                    "models" + File.separator + model.getProjectId() + "_" + model.getVersion() + File.separator;
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
    @GetMapping("/project/{projectId}/data_export/annotations/{format}")
    public ResponseEntity<ByteArrayResource> exportAnnotation(@PathVariable String format,
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
    @GetMapping("/project/{projectId}/data_export/predictions/{format}")
    public ResponseEntity<ByteArrayResource> exportPrediction(@PathVariable String format,
                                                              @PathVariable BigInteger projectId) {

        ByteArrayResource resource = ExportUtils.exportFile(false, format,
                Collections.singletonList(predictionMapper.listByProjectId(projectId)));

        return getResponse(resource);
    }

    /**
     * Helper method for the response entity creation
     *
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
