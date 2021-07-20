package com.uroom.backend.Models.POJOS;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


public class EmailPOJO {
    /*

    private String host;
    private int port;
    private String username;
    private String password;
*/
    private Email_type type;
    private JavaMailSenderImpl mailSender;
    private String from;
    private String to;
    private String question;
    private String who;

    public EmailPOJO(String host, int port, String username, String password){
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        this.from = "no-reply@uroom.com.co";
    }

    public void send_email() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Create mail instance
        //SimpleMailMessage mailMessage = new SimpleMailMessage();
        helper.setFrom(this.from, "URoom");
        helper.setTo(this.to);
        if(this.type == Email_type.QUESTION){
            helper.setSubject("Nueva pregunta de: " + this.who);
            helper.setText(template_question(this.who, this.question), true);
        }
        this.mailSender.send(message);
    }


    public String template_question(String name, String question){
        String content = "\n" +
                "<style>\n" +
                "@import url('https://fonts.googleapis.com/css2?family=Architects+Daughter&family=Jost:wght@200&family=Knewave&family=Permanent+Marker&family=Trade+Winds&display=swap');\n" +
                "</style>\n" +
                "\n" +
                "<h3 style=\"font-family:'Permanent Marker', cursive; display: inline\"> Tienes una nueva pregunta en una de tus publicaciones: </h3>\n" +
                "<div style=\"\n" +
                "  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);\n" +
                "  transition: 0.3s;\n" +
                "  background-color: #670071\">\n" +
                "  \n" +
                "  <div style=\"padding: 16px;\">\n" +
                "    <h3 style=\"font-family:'Jost', cursive;\n" +
                "              display: inline;\n" +
                "              color: white;\n" +
                "              \"> "+ name + " pregunta: </h3>\n" +
                "  \n" +
                "  \n" +
                "  </div>\n" +
                "  \n" +
                "  \n" +
                "  <div style=\"padding: 2px 16px;\n" +
                "              background-color: #FFFF\">\n" +
                "    <p>" + question + "</p>\n" +
                "      <button style = \"background-color: #670071;\n" +
                "                       color: white;\n" +
                "                       padding: 10px;\n" +
                "                       border-radius: 10%;\n" +
                "                       font-family:'Jost', cursive;\">\n" +
                "                       <h3 style = \"margin: 0px 20px\">\n" +
                "                          Responder                 \n" +
                "                       </h3>\n" +
                "      \n" +
                "    </button>\n" +
                "  </div>\n" +
                "  \n" +
                "</div>\n";
        return content;
    }

    public Email_type getType() {
        return type;
    }

    public void setType(Email_type type) {
        this.type = type;
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
