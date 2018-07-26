package com.example.conference.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class webConfiguration implements WebMvcConfigurer {

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/startList").setViewName("startList.html");
        registry.addViewController("/").setViewName("startList.html");
        registry.addViewController("/registrationPerson").setViewName("registrationPerson.html");
        registry.addViewController("/settingPerson").setViewName("settingPerson.html");
        registry.addViewController("/listOfPerson").setViewName("listOfPerson.html");
        registry.addViewController("/listOfReports").setViewName("listOfReports.html");
    }*/
}