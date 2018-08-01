package com.example.conference.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class webConfiguration implements WebMvcConfigurer {

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/startList").setViewName("startList.html");
        registry.addViewController("/").setViewName("startList.html");
        registry.addViewController("/registrationPerson").setViewName("registrationUser.html");
        registry.addViewController("/settingPerson").setViewName("settingUser.html");
        registry.addViewController("/listOfPerson").setViewName("listOfUser.html");
        registry.addViewController("/listOfReports").setViewName("addPresentation.html");
    }*/
}