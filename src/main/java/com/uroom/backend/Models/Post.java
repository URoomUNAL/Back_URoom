package com.uroom.backend.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  int id;

    @Size(max = 255)
    @Column(name="description", nullable=true, length = 255)
    private String description;

    @NotBlank
    @Size(max = 45)
    @NotNull
    @Column(name="address", nullable=false, length = 45)
    private String address;

    @NotNull
    @Column(name="price", nullable=false)
    private int price;

    @NotNull
    @Column(name="latitude", nullable=false)
    private double latitude;

    @NotNull
    @Column(name="longitude", nullable=false)
    private double longitude;

    @NotBlank
    @Size(max = 255)
    @NotNull
    @Column(name="main_img", nullable=false, length = 255)
    private String main_img;

    @Column(name = "is_active", nullable = false)
    private boolean is_active;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    @ManyToMany(mappedBy = "posts")
    private Set<Rule> rules;

    @ManyToMany(mappedBy = "posts")
    private Set<Service> services;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}