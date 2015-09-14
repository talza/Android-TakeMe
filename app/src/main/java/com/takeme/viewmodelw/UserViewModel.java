package com.takeme.viewmodelw;

import android.databinding.Bindable;

import android.databinding.BaseObservable;

import com.takeme.takemeapp.BR;
import com.takeme.takemeapp.R;

/**
 * Created by tzamir on 9/2/2015.
 */
public class UserViewModel extends BaseObservable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
        notifyPropertyChanged(R.id.btnSignUp);
    }
}