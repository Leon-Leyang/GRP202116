package com.grp202116.sidecarserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Bean;

/**
 * This is the application class of the sidecar server,
 * which is built for the registration of the flask server to eureka server.
 * Now the consumer server can use API written by python or other non-JVM languages.
 *
 * For the official sidecar documentation, visit
 * https://cloud.spring.io/spring-cloud-netflix/multi/multi__polyglot_support_with_sidecar.html
 *
 * @author Yujie Chen
 * @version 1.2
 */
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
