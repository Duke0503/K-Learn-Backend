package com.klearn.klearn_website.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Email sender address from application properties
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Sends a plain text reminder email to the specified recipient.
     * 
     * @param toEmail Recipient's email address
     * @param subject Subject of the email
     * @param body Body of the email
     */
    public void sendReminderEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * Sends a plain text email to the specified recipient.
     * 
     * @param to Recipient's email address
     * @param subject Subject of the email
     * @param body Body of the email
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
