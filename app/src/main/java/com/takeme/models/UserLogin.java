package com.takeme.models;

import com.takeme.models.User;

/**
 * Created by tzamir on 9/24/2015.
 * This class represent model of Login.
 */
public class UserLogin {
    private String email;
    private String password;
    private String registrationDeviceKey;

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

    public String getRegistrationDeviceKey() {
        return registrationDeviceKey;
    }

    public void setRegistrationDeviceKey(String registrationDeviceKey) {
        this.registrationDeviceKey = registrationDeviceKey;
    }
}
