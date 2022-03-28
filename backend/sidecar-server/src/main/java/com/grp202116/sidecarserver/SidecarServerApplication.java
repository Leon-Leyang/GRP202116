package com.grp202116.sidecarserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Bean;

@EnableSidecar
@SpringBootApplication
public class SidecarServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SidecarServerApplication.class, args);
    }

    @Bean
    public SpringClientFactory springClientFactory() {
        return new SpringClientFactory();
    }
}
