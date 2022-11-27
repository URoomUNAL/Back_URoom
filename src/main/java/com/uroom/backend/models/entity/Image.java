package com.uroom.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Image {
    @Id
    @NotBlank
    @Size(max = 255)
    @NotNull
    @Column(name="url", nullable=false)
    private String url;

    @JsonIgnore
    @ManyToOne()
    @NotNull
    @JoinColumn(name = "post")
    private Post post;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
