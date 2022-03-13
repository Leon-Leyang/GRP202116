package com.grp202116.backend.controller;

import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

/**
 * Just for test
 */
@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/java-user")
    public String JavaUser() {
        return "{'username': 'java', 'password': 'java'}";
    }

    @RequestMapping("/python-user")
    public String PythonUser() {
        return restTemplate.getForEntity("http://sidecar-server/getUser", String.class).getBody();
    }

    @GetMapping("/model/{modelId}")
    public String ModelTest(@PathVariable BigInteger modelId) {
        restTemplate.getForEntity("http://sidecar-server/model/" + modelId, String.class).getBody();
        return "{Success}";
    }
}