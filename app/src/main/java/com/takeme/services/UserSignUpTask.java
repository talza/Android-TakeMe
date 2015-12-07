package com.takeme.services;

import com.takeme.models.User;
import com.takeme.models.UserToken;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class UserSignUpTask implements Callback<UserToken> {

    private UserSignUpResponse userSignUpResponse;
    private User user;

    public UserSignUpTask(String email, String password, String firstName, String lastName, String phoneNumber, String registrationDeviceKey, UserSignUpResponse userRegisterRespond){
        this.userSignUpResponse = userRegisterRespond;
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setRegistrationDeviceKey(registrationDeviceKey);
    }

    public void signUp(){
        Call<UserToken> call = TakeMeRestClient.getInstance().service().signUp(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<UserToken> response) {

        if (response.isSuccess()){
            userSignUpResponse.onRegisterSuccess(response.body());
        }
        else{
            userSignUpResponse.onRegisterFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        userSignUpResponse.onRestCallError(t);
    }


    public interface UserSignUpResponse {

        public void onRegisterSuccess(UserToken id);

        public void onRegisterFailed();

        public void onRestCallError(Throwable t);
    }

}
