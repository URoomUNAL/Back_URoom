package com.uroom.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotBlank
    @Size(max = 45)
    @NotNull
    @Column(name="name", nullable=false, length = 45)
    private String name;

    @NotBlank
    @Size(max = 45)
    @NotNull
    @Column(name="icono", nullable=false, length = 45)
    private String icono;

    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    private Set<Post> posts;

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

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


}
