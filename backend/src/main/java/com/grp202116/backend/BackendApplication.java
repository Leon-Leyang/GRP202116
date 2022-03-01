package com.grp202116.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The class BackendApplication is the main class of the backend of the whole project which runs the program
 */
@SpringBootApplication
public class BackendApplication {
    /**
     * The running method
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
