package com.credable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@EnableRetry
@SpringBootApplication
public class CredableEngineApplication {


    public static void main(String[] args) {
        SpringApplication.run(CredableEngineApplication.class, args);
    }


}
