package com.uroom.backend.Models.ResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uroom.backend.Models.EntitiyModels.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class PostResponse {
    private int id;
    private String title;
    private String description;
    private String address;
    private int price;
    private double latitude;
    private double longitude;
    private String main_img;
    private boolean is_active;
    private Double score;
    private List<Image> images;
    private List<CalificationResponse> califications;
    private List<Question> questions;
    private Set<Rule> rules;
    private Set<Service> services;
    private User user;

    public PostResponse(Post post){
        this.address = post.getAddress();
        this.id = post.getId();
        this.description = post.getDescription();
        this.main_img = post.getMain_img();
        this.images = post.getImages();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.is_active = post.isIs_active();
        this.rules = post.getRules();
        this.services = post.getServices();
        this.score = post.getScore();
        this.price = post.getPrice();
        this.title = post.getTitle();
        this.user = post.getUser();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<CalificationResponse> getCalifications() {
        return califications;
    }

    public void setCalifications(List<CalificationResponse> califications) {
        this.califications = califications;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
