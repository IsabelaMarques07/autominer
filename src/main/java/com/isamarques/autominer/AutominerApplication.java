package com.isamarques.autominer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AutominerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutominerApplication.class, args);
    }
}