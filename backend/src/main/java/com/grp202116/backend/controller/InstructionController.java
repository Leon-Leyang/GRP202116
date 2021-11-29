package com.grp202116.backend.controller;

import com.grp202116.backend.mapper.InstructionMapper;
import com.grp202116.backend.pojo.InstructionDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
public class InstructionController {
    @Resource
    private InstructionMapper instructionMapper;

    @GetMapping("/instructions/{projectId}")
    public InstructionDO getInstruction(@PathVariable BigInteger projectId){
        return instructionMapper.getByProjectId(projectId);
    }

    @PostMapping("/instructions/{projectId}")
    public void updateInstruction(@PathVariable BigInteger projectId, @RequestBody InstructionDO instruction){
        instructionMapper.deleteByProjectId(projectId);
        instructionMapper.insert(instruction);
    }
}
