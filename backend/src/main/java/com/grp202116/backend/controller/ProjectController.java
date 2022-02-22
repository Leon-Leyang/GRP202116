package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.mapper.ProjectMapper;
import com.grp202116.backend.pojo.ProjectDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@RestController
public class ProjectController {
    @Resource
    private ProjectMapper projectMapper;

    @GetMapping("/projects/{projectId}")
    public ProjectDO getProject(@PathVariable BigInteger projectId){
        return projectMapper.getByProjectId((projectId));
    }

    @GetMapping("/projects")
    public List<ProjectDO> listAllProjects(){
        return projectMapper.listAll();
    }

    @GetMapping("/projects/edit/{projectId}")
    public void editProject(@PathVariable BigInteger projectId){
        projectMapper.edit(projectId);
    }

    @PostMapping("/projects")
    public void addProject(@RequestBody ProjectDO project){
        projectMapper.insert(project);
    }

    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable BigInteger projectId){
        projectMapper.deleteByProjectId(projectId);
    }
}
