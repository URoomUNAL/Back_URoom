package com.uroom.backend.Models.EntitiyModels;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Interested {

    @EmbeddedId
    private InterestedKey id;

    @ManyToOne
    @MapsId("UserId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("PostId")
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    private Date date;

    public Interested(InterestedKey id, User user, Post post, Date date) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.date = date;
    }

    public Interested(){
    }

    public InterestedKey getId() {
        return id;
    }

    public void setId(InterestedKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
