package com.example.conference.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("schedule");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/schedule").setViewName("schedule");
        registry.addViewController("/exit").setViewName("exit");
    }
}
