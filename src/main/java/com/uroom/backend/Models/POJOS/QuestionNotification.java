package com.uroom.backend.Models.POJOS;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class QuestionNotification {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String question;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
