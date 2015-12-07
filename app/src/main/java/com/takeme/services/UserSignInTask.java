package com.takeme.services;

import com.takeme.models.UserToken;
import com.takeme.models.UserLogin;

import retrofit.Callback;
import retrofit.Response;


public class UserSignInTask implements Callback<UserToken> {

    private UserLoginResponse userLoginResponse;
    private UserLogin userLogin;

    public UserSignInTask(String email, String password, String registrationDeviceKey, UserLoginResponse userLoginResponse){
        this.userLoginResponse = userLoginResponse;
        userLogin = new UserLogin();
        userLogin.setEmail(email);
        userLogin.setPassword(password);
        userLogin.setRegistrationDeviceKey(registrationDeviceKey);
    }

    public void signIn(){

        retrofit.Call<UserToken> call = TakeMeRestClient.getInstance().service().signIn(this.userLogin);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<UserToken> response) {
        if (response.isSuccess()){
            userLoginResponse.onLoginSuccess(response.body());
        }else{
            userLoginResponse.onLoginFailed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        userLoginResponse.onRestCallError(t);
    }

    public interface UserLoginResponse {

        public void onLoginSuccess(UserToken id);

        public void onLoginFailed();

        public void onRestCallError(Throwable t);
    }

}
