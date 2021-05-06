package com.uroom.backend.Models.EntitiyModels;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
class QuestionId implements Serializable {
    private Post post;
    private User user;

    public QuestionId(Post post, User user){
        this.post = post;
        this.user = user;
    }
}
*/
@Entity
//@IdClass(QuestionId.class)
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="anonymous", nullable = false)
    private boolean anonymous;

    @NotBlank
    @Size(max=255)
    @NotNull
    @Column(name="question", nullable = false, length = 255)
    private String question;

    @NotBlank
    @Size(max=255)
    @Column(name="answer", length = 255)
    private String answer;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
