package com.uroom.backend.Services;

import com.uroom.backend.auth.configuration.EmailConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl mailSender;
    private final EmailConfiguration emailConfiguration;
    @Value("${uroom.info-mail-sender}")
    private String from;
    @Value("${uroom.info-mail-sender-name")
    private String personal;


    public EmailService(TemplateEngine templateEngine, JavaMailSenderImpl mailSender, EmailConfiguration emailConfiguration) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setPassword(emailConfiguration.getPassword());
        this.emailConfiguration = emailConfiguration;
    }


    public void send_question_email(String to, String who, String question) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Create mail instance
        //SimpleMailMessage mailMessage = new SimpleMailMessage();
        helper.setFrom(from, personal);
        helper.setTo(to);
        helper.setSubject("Nueva pregunta de: " + who);

        Context context = new Context();
        context.setVariable("name", who);
        context.setVariable("question", question);
        String process = templateEngine.process("question-template.html", context);
        helper.setText(process, true);
        this.mailSender.send(message);
    }
}
