package com.rhombus.concrete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConcreteApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConcreteApiApplication.class, args);
    }
}


