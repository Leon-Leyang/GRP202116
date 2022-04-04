package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.mapper.LabelInterfaceMapper;
import com.grp202116.consumerserver.pojo.LabelInterfaceDO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * The Class LabelInterfaceController, control the Label Interface of the project
 * Control the insertion, deletion and update of the Label Interface
 *
 * @author Yujie Chen
 * @version 1.2
 */
@Deprecated
@RestController
public class LabelInterfaceController {
    public static final List<Integer> templatesProjectId = null;
    @Resource
    private LabelInterfaceMapper labelInterfaceMapper;

    /**
     * Get the Label Interface of certain project
     *
     * @param projectId the projectId fetched from the mapper
     */
    @GetMapping("/interface/{projectId}")
    public void getLabelInterface(@PathVariable BigInteger projectId) {
        labelInterfaceMapper.getByProjectId(projectId);
    }

    /**
     * Update the Label Interface in certain project by first delete the origin Label Interface,
     * then insert the new Label Interface
     *
     * @param projectId      the projectId fetched from the mapper
     * @param labelInterface the newly set Label Interface
     */
    @PostMapping("/interface/{projectId}")
    public void updateLabelInterface(@PathVariable BigInteger projectId, @RequestBody LabelInterfaceDO labelInterface) {
        labelInterfaceMapper.deleteByProjectId(projectId);
        labelInterfaceMapper.insert(labelInterface);
    }

    /**
     * Get the set templates of the Label Interface
     *
     * @return return the set templates of the Label Interface
     */
    @GetMapping("/interface/templates")
    public List<LabelInterfaceDO> getTemplates() {
        return null;
    }
}
