package com.uroom.backend.Models.EntitiyModels;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
    private int id;

    @NotBlank
    @Size(max=45)
    @NotNull
    @Column(name="title", nullable = false, length = 45)
    private String title;

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

    @Column(name = "score")
    private Double score;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Calification> califications;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Question> questions;

    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private List<User> users_favorite;

    @ManyToMany(/*fetch = FetchType.EAGER, cascade = CascadeType.ALL*/)
    @JoinTable(
            name = "post_rule", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")}
    )
    private Set<Rule> rules;

    @ManyToMany(/*fetch = FetchType.EAGER, cascade = CascadeType.ALL*/)
    @JoinTable(
            name = "post_service", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private Set<Service> services;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private Set<Interested> interestedUsers;

    @NotNull
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Visit> visits;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Double getScore() { return score; }

    public void setScore(Double score) { this.score = score; }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Interested> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(Set<Interested> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
