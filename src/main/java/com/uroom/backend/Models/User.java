package com.uroom.backend.Models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotBlank
    @Size(max = 45)
    @NotNull
    @Column(name="email", nullable=false, length = 45, unique=true)
    private String email;

    @NotBlank
    @Size(max = 20)
    @NotNull
    @Column(name="password", nullable=false, length=20)
    private String password;

    @Column(name="age",nullable=true)
    private Integer age;

    @NotBlank
    @NotNull
    @Length(max = 50)
    @Column(name="cellphone", nullable=false, length=15, unique=true)
    private String cellphone;

    @NotNull
    @Column(name="isStudent", nullable=false)
    private boolean is_student;

    @NotNull
    @Column(name="isActive", nullable=false)
    private boolean is_active;

    //Información Adicional
    //Foto foto?
    //Ennumerate Gustos


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
}
