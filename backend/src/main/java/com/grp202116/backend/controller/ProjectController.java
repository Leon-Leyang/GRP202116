package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.mapper.ProjectMapper;
import com.grp202116.backend.pojo.ProjectDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
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

    /**
     * Get the Project by the id
     * @param projectId the projectId fetched from the mapper
     * @return return the Project of corresponding projectId
     */
    @GetMapping("/projects/{projectId}")
    public ProjectDO getProject(@PathVariable BigInteger projectId){
        return projectMapper.getByProjectId((projectId));
    }

    /**
     * List all the Project
     * @return return all the created Project
     */
    @GetMapping("/projects")
    public List<ProjectDO> listAllProjects(){
        return projectMapper.listAll();
    }

    /**
     * Edit the Project information, including edit
     * the name, the type, and the description of the Project
     * @param project the projectId fetched from the mapper
     */
    @PostMapping("/projects/edit")
    public void editProject(@RequestBody ProjectDO project){
        project.setUpdateTime(new Date());
        projectMapper.edit(project);
    }

    /**
     * Add the Project to the database, set the created time and update time
     * @param project the newly set Project
     */
    @PostMapping("/projects")
    public void addProject(@RequestBody ProjectDO project){
        Date date = new Date();
        project.setCreateTime(date);
        project.setUpdateTime(date);

        projectMapper.insert(project);
    }

    /**
     * Delete the Project by the id
     * @param projectId the projectId fetched from the mapper
     */
    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable BigInteger projectId){
        projectMapper.deleteByProjectId(projectId);
    }
}
