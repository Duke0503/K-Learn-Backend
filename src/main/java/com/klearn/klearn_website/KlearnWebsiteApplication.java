package com.klearn.klearn_website;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.klearn.klearn_website.mapper")
public class KlearnWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlearnWebsiteApplication.class, args);
    }

}
