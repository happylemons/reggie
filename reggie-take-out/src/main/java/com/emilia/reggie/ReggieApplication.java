package com.emilia.reggie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ServletComponentScan("com.emilia.reggie.filter")
@EnableCaching
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
    }

}
