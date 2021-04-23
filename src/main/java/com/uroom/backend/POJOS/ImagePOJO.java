package com.uroom.backend.POJOS;

import com.uroom.backend.Models.Post;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImagePOJO {
    private String url;
    private PostPOJO post;

    public ImagePOJO(){}
    public void setUrl(String url) {
        this.url = url;
    }
    /*
    public void setPost(PostJO post) {
        this.post = post;
    }
    public PostPOJO getPost() {
        return post;
    }
    /**/

    public String getUrl() {
        return url;
    }

}
