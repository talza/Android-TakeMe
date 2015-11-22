package com.takeme.services;

import com.takeme.models.User;
import com.takeme.models.UserToken;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class UserGetByFacebookTask implements Callback<UserToken> {

    private UserGetByFacebookResponse userGetByFacebookResponse;
    private String token;

    public UserGetByFacebookTask(String token, UserGetByFacebookResponse userGetByFacebookResponse){
        this.userGetByFacebookResponse = userGetByFacebookResponse;
        this.token = token;
    }

    public void getUserByFacbook(){
        Call<UserToken> call = TakeMeRestClient.getInstance().service().getUserByFacbook(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<UserToken> response) {

        if (response.isSuccess()){
            userGetByFacebookResponse.onUserGetByFacebookSuccess(response.body());
        }
        else{
            userGetByFacebookResponse.onUserGetByFacebookFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        userGetByFacebookResponse.onRestCallError(t);
    }


    public interface UserGetByFacebookResponse {

        public void onUserGetByFacebookSuccess(UserToken user);

        public void onUserGetByFacebookFailed();

        public void onRestCallError(Throwable t);
    }

}
