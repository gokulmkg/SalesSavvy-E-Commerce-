package com.kodnest.ToyGalaxy.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailService {

    private final JavaMailSender mailSender;

    public emailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${spring.mail.properties.mail.smtp.from}")
    private  String fromEmMail;


    public void sendEmail(String to,String subject,String body) {
try {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromEmMail);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
} catch (Exception e) {
    throw new RuntimeException(e.getMessage());
}
}
}
