package com.uroom.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    //Información Básica y Obligatoria
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="name", nullable=false, length = 60)
    @Size(max = 60)
    @NotNull
    @NotBlank
    private String name;

    @Column(name="distinguishedName", nullable=false)
    @Size(max = 255)
    @NotNull
    @NotBlank
    private String distinguishedName;

    @NotBlank
    @Size(max = 45)
    @NotNull
    @Column(name="email", nullable=false, length = 45, unique=true)
    private String email;

    @Transient
    private String password;

    @Transient
    private Integer age;

    @Transient
    private String cellphone;

    @NotNull
    @Column(name="is_Student", nullable=false)
    private boolean is_student;

    @NotNull
    @Column(name="is_Active", nullable=false)
    private boolean is_active;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="Favorites",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="post_id")
    )
    private Set<Post> favorites;

    //Información Adicional
    //Ennumerate Gustos

    @Column(name="photo")
    private String photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Calification> calification;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Interested> interestedPosts;

    @Column(name = "score")
    private Double score;

    public User(String name, String email, String password, String cellphone, boolean is_student){
        this.cellphone = cellphone;
        this.email = email;
        this. password = password;
        this.name = name;
        this.is_student = is_student;
        this.is_active = true;
    }
    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isIs_student() {
        return is_student;
    }

    public void setIs_student(boolean is_student) {
        this.is_student = is_student;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<Post> getPost() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Calification> getCalification() {
        return calification;
    }

    public void setCalification(List<Calification> calification) {
        this.calification = calification;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<Post> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Post> favorites) {
        this.favorites = favorites;
    }

    public Set<Interested> getInterestedPosts() {
        return interestedPosts;
    }

    public void setInterestedPosts(Set<Interested> interestedPosts) {
        this.interestedPosts = interestedPosts;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
