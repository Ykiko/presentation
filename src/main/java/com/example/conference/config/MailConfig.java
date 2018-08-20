package com.example.conference.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSend = new JavaMailSenderImpl();

        mailSend.setHost(host);
        mailSend.setPort(port);
        mailSend.setUsername(username);
        mailSend.setPassword(password);

        Properties properties = mailSend.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSend;
    }
}
