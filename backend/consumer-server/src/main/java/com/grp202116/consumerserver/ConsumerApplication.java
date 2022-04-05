package com.grp202116.consumerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * This is the application class of the consumer server,
 * where the majority of core backend functions are implemented,
 * including database operations and http requests.
 *
 * For the official eureka client reference, visit
 * https://cloud.spring.io/spring-cloud-netflix/reference/html/
 *
 * @author Yujie Chen
 * @version 1.2
 */
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {

    /**
     * The starter method of this consumer server.
     *
     * @param args default springboot args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /**
     * Create the bean of spring cloud {@link RestTemplate}
     * for transitions between this server and the flask server
     *
     * @return {@link RestTemplate}
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
