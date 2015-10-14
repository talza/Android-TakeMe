package com.takeme.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.takeme.takemeapp.BR;
import com.takeme.takemeapp.R;

/**
 * Created by tzamir on 9/2/2015.
 */
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}