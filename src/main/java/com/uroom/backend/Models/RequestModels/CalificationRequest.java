package com.uroom.backend.Models.RequestModels;

public class CalificationRequest {

    private int post_id;
    private String user_username;
    private double score;
    private String comment;

    public CalificationRequest(int post_id, String user_username, double score, String comment) {
        this.post_id = post_id;
        this.user_username = user_username;
        this.score = score;
        this.comment = comment;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
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
}
