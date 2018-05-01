package com.home.spring.servlet3_async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableAsync
@ServletComponentScan
public class Servlet3AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(Servlet3AsyncApplication.class, args);
    }
}
