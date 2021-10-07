package ru.bogdanov.bucketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BucketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BucketServiceApplication.class, args);
    }

}
