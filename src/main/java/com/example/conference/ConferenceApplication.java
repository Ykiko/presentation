package com.example.conference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@SpringBootApplication
public class ConferenceApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(ConferenceApplication.class, args);
    }
}
