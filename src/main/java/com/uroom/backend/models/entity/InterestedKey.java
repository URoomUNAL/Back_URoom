package com.uroom.backend.models.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InterestedKey implements Serializable {

    @Column(name = "user_id")
    public int UserId;

    @Column(name = "post_id")
    public int PostId;

    public InterestedKey(int userId, int postId) {
        UserId = userId;
        PostId = postId;
    }

    public InterestedKey() {
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }
}
