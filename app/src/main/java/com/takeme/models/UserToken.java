package com.takeme.models;

/**
 * Created by tzamir on 10/10/2015.
 * This model represent model of token of user.
 */
public class UserToken {
    private Long id;

    public UserToken() {
    }

    public Long getId() {
        return id;
    }

    public void setToken(Long id) {
        this.id = id;
    }
}
