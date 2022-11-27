package com.uroom.backend.models.responses;

import com.uroom.backend.models.entity.Question;

public class QuestionResponse {
    private int id;
    private String user_name;
    private boolean anonymous;
    private String question;
    private String answer;

    public QuestionResponse(Question question){
        this.id = question.getId();
        this.user_name = question.getUser().getName();
        this.anonymous = question.isAnonymous();
        this.question = question.getQuestion();
        this.answer = question.getAnswer();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUser_name() { return user_name; }

    public void setUser_name(String user_name) { this.user_name = user_name; }

    public boolean isAnonymous() { return anonymous; }

    public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }
}
