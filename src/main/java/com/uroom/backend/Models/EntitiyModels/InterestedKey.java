package com.uroom.backend.Models.EntitiyModels;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
