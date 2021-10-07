package com.web.blabber.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class BlabberApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlabberApplication.class, args);
    }
}
