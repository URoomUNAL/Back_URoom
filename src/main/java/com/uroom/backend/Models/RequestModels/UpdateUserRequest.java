package com.uroom.backend.Models.RequestModels;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUserRequest {

    private String name;
    private String age;
    private String cellphone;
    private String photo;
    private MultipartFile photo_file;

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
