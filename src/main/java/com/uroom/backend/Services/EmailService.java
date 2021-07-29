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
import java.util.Properties;

@Service
public class EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl mailSender;
    private final EmailConfiguration emailConfiguration;
    @Value("${uroom.info-mail-sender}")
    private String from;
    @Value("${uroom.info-mail-sender-name}")
    private String personal;
    @Value("${uroom.frontend-url}")
    private String link;


    public EmailService(TemplateEngine templateEngine, JavaMailSenderImpl mailSender, EmailConfiguration emailConfiguration) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setPassword(emailConfiguration.getPassword());
        Properties props = mailSender.getJavaMailProperties();
        this.emailConfiguration = emailConfiguration;
    }


    public void send_question_email(String to, String who, String question, int post_id) throws MessagingException, UnsupportedEncodingException {
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
        context.setVariable("link", this.link + "Post/" + post_id);
        String process = templateEngine.process("question-template.html", context);
        helper.setText(process, true);
        this.mailSender.send(message);
    }


    public void send_rent_email(String to, String who, int post_id) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Create mail instance
        //SimpleMailMessage mailMessage = new SimpleMailMessage();
        helper.setFrom(from, personal);
        helper.setTo(to);
        helper.setSubject("Felicidades por tu nueva habitación");

        Context context = new Context();
        context.setVariable("name", who);
        context.setVariable("link", this.link + "Post/" + post_id);
        String process = templateEngine.process("rent-template.html", context);
        helper.setText(process, true);
        this.mailSender.send(message);
    }
}
