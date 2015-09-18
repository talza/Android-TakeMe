package com.takeme.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.takeme.takemeapp.BR;
import com.takeme.takemeapp.R;

/**
 * Created by tzamir on 9/2/2015.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}