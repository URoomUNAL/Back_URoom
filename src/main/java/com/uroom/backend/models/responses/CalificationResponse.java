package com.uroom.backend.models.responses;

import com.uroom.backend.models.entity.Calification;

public class CalificationResponse {

    private int id;
    private String user_name;
    private double score;
    private String comment;
    private String pros;
    private String cons;

    public CalificationResponse(Calification calification){
        this.id = calification.getId();
        this.user_name = calification.getUser().getName();
        this.score = calification.getScore();
        this.comment = calification.getComment();
        this.pros = calification.getPros();
        this.cons = calification.getCons();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPros() { return pros; }

    public void setPros(String pros) { this.pros = pros; }

    public String getCons() { return cons; }

    public void setCons(String cons) { this.cons = cons; }
}
