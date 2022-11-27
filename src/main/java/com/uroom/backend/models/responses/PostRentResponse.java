package com.uroom.backend.models.responses;

import com.uroom.backend.models.entity.*;

public class PostRentResponse {
    private int id;
    private int rent_id;
    private String rent_status;
    private String title;
    private String address;
    private int price;
    private String main_img;
    private boolean is_rated;
    private Double score;
    private CalificationResponse calification;

    public PostRentResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.address = post.getAddress();
        this.price = post.getPrice();
        this.main_img = post.getMain_img();
        this.score = post.getScore();
        this.is_rated = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public boolean isIs_rated() {
        return is_rated;
    }

    public void setIs_rated(boolean is_rated) {
        this.is_rated = is_rated;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public CalificationResponse getCalification() {
        return calification;
    }

    public void setCalification(CalificationResponse calification) {
        this.calification = calification;
    }

    public int getRent_id() {
        return rent_id;
    }

    public void setRent_id(int rent_id) {
        this.rent_id = rent_id;
    }

    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
    }
}
