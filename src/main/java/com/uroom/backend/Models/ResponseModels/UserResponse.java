package com.uroom.backend.Models.ResponseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uroom.backend.Models.EntitiyModels.Post;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserResponse {
    //TODO: AÑADIR TOKEN, IMÁGEN
    private int id;
    private String name;
    private String email;
    private Integer age;
    private String cellphone;
    private boolean is_student;
    private boolean is_active;
    private List<Post> posts;
}
