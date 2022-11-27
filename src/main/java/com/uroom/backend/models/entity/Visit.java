package com.uroom.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne()
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "post")
    private Post post;

    @NotNull
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
