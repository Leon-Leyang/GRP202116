package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.DataMapper;
import com.grp202116.backend.mapper.ProjectMapper;
import com.grp202116.backend.pojo.ProjectDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
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

    @PostMapping("/projects/edit")
    public void editProject(@RequestBody ProjectDO project){
        project.setUpdateTime(new Date());
        projectMapper.edit(project);
    }

    @PostMapping("/projects")
    public void addProject(@RequestBody ProjectDO project){
        Date date = new Date();
        project.setCreateTime(date);
        project.setUpdateTime(date);

        projectMapper.insert(project);
    }

    @DeleteMapping("/projects/{projectId}")
    public void deleteProject(@PathVariable BigInteger projectId){
        projectMapper.deleteByProjectId(projectId);
    }
}
