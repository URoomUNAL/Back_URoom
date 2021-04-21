package com.uroom.backend.POJOS;

import com.uroom.backend.Models.Image;
import com.uroom.backend.Models.Rule;
import com.uroom.backend.Models.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class PostPOJO {
    private String description;
    private String address;
    private int price;
    private double latitude;
    private double longitude;
    private MultipartFile main_img;

    public PostPOJO() {
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

    /*private List<MultipartFile> images;*/


    /*
    private Set<RulePOJO> rules;
    private Set<ServicePOJO> services;*/
}
