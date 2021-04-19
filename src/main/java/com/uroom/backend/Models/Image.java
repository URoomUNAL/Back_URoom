package com.uroom.backend.Models;

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
    @Column(name="url", nullable=false, length = 255)
    private String url;

    @ManyToOne()
    @JoinColumn(name = "university_id")
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
