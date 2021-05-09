package com.uroom.backend.Models.RequestModels;

import com.uroom.backend.Models.EntitiyModels.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserRequest {

    private int id;
    private String email;
    private boolean is_student;
    private List<Post> posts;
    //Información Actualizable
    private String name;
    private Integer age;
    private String cellphone;
    private boolean is_active;
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

    public boolean isIs_student() {
        return is_student;
    }

    public void setIs_student(boolean is_student) {
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
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
}
