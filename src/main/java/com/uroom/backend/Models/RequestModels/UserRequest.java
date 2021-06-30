package com.uroom.backend.Models.RequestModels;

import com.uroom.backend.Models.EntitiyModels.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class UserRequest {

    private int id;
    private String email;
    private String is_student;
    private List<Post> posts;
    //Información Actualizable
    private Set<Post> favorites;
    private String name;
    private String age;
    private String cellphone;
    private String is_active;
    private String password;
    private String photo;
    private MultipartFile photo_file;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String isIs_student() {
        return is_student;
    }

    public void setIs_student(String is_student) {
        this.is_student = is_student;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String isIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public MultipartFile getPhoto_file() {
        return photo_file;
    }

    public void setPhoto_file(MultipartFile photo_file) {
        this.photo_file = photo_file;
    }

    public Set<Post> getFavorites() { return favorites; }

    public void setFavorites(Set<Post> favorites) { this.favorites = favorites; }
}
