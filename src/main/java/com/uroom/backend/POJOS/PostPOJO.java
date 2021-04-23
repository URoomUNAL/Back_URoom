package com.uroom.backend.POJOS;

import com.uroom.backend.Models.Image;
import com.uroom.backend.Models.Rule;
import com.uroom.backend.Models.Service;
import com.uroom.backend.Models.User;
import com.uroom.backend.Services.RuleService;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class PostPOJO {
    private String title;
    private String description;
    private String address;
    private int price;
    private double latitude;
    private double longitude;
    private double score;
    private MultipartFile main_img;
    private List<MultipartFile> images;
    private Set<String> rules;
    private Set<String> services;
    private String user;

    public PostPOJO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public void setRules(Set<String> rules) {
        this.rules = rules;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public Set<String> getRules() {
        return rules;
    }

    public Set<String> getServices() {
        return services;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMain_img(MultipartFile main_img) {
        this.main_img = main_img;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public MultipartFile getMain_img() {
        return main_img;
    }

    public double getScore() { return score; }

    public void setScore(double score) { this.score = score; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    /*private List<MultipartFile> images;*/


    /*
    private Set<RulePOJO> rules;
    private Set<ServicePOJO> services;*/
}
