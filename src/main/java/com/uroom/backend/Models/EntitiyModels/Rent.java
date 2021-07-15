package com.uroom.backend.Models.EntitiyModels;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne()
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        RENT, ENDED;
    }

    @Column(name = "begin", columnDefinition = "DATE")
    private LocalDate begin;

    @Column(name = "begin", columnDefinition = "DATE")
    private LocalDate end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isRent(){
        return getStatus() == Status.RENT;
    }
}


