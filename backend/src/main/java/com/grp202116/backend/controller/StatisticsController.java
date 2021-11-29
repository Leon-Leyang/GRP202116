package com.grp202116.backend.controller;

import com.grp202116.backend.statistics.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class StatisticsController {
    @GetMapping("/statistics/{projectId}")
    public Statistics getStatistics(@PathVariable BigInteger projectId){
        Statistics statistics = new Statistics(projectId);
        return statistics;
    }
}
