package com.takeme.models;

import com.takeme.models.User;

/**
 * Created by tzamir on 9/24/2015.
 */
public class UserLogin {
    private String email;
    private String password;


    public UserLogin()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
