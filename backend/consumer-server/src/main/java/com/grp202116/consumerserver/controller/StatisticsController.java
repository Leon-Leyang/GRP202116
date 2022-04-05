package com.grp202116.consumerserver.controller;

import com.grp202116.consumerserver.statistics.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;

/**
 * The Class StatisticsController, control the Statistics of the project
 * Control the list of the Statistics
 *
 * @author Yujie Chen
 * @version 1.2
 * @see Statistics
 */
@RestController
public class StatisticsController {
    /**
     * Get the Statistics of certain project
     *
     * @param projectId the projectId fetched from the mapper
     * @return return the Statistics of corresponding projectId
     */
    @GetMapping("/statistics/{projectId}")
    public Statistics getStatistics(@PathVariable BigInteger projectId) throws IOException {
        return new Statistics().setProjectId(projectId);
    }
}
