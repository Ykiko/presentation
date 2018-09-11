package com.example.conference.service;

import com.example.conference.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailSend {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Bean
    public static JavaMailSender getMailSender(MailConfig config) {
        JavaMailSenderImpl mailSend = new JavaMailSenderImpl();

        mailSend.setHost(config.getHost());
        mailSend.setPort(config.getPort());
        mailSend.setUsername(config.getUsername());
        mailSend.setPassword(config.getPassword());

        Properties properties = mailSend.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", config.getProtocol());
        properties.setProperty("mail.debug", config.getDebug());

        return mailSend;
    }
    @Autowired
    public MailSend(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
