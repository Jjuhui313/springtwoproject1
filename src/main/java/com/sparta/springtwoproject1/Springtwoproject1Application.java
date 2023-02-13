package com.sparta.springtwoproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springtwoproject1Application {

    public static void main(String[] args) {

        SpringApplication.run(Springtwoproject1Application.class, args);
    }

}
