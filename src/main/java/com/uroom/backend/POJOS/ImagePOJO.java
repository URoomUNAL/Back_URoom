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
    private Post post;
}
