package com.uroom.backend.Models.ResponseModels;

import com.uroom.backend.Models.EntitiyModels.User;

public class UserInterestedResponse {

    private int id;
    private String name;

    public UserInterestedResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
