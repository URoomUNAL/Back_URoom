package com.uroom.backend.models.requests;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class PostRequest {
    private String title;
    private String description;
    private String address;
    private int price;
    private double latitude;
    private double longitude;
    private double score;
    private MultipartFile main_img;
    private String main_img_s;
    private List<String> images_s;
    private List<MultipartFile> images;
    private Set<String> rules;
    private Set<String> services;
    private String user;
    private boolean is_active;


    public String getMain_img_s() {
        return main_img_s;
    }

    public void setMain_img_s(String main_img_s) {
        this.main_img_s = main_img_s;
    }

    public List<String> getImages_s() {
        return images_s;
    }

    public void setImages_s(List<String> images_s) {
        this.images_s = images_s;
    }

    public PostRequest() {
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
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
