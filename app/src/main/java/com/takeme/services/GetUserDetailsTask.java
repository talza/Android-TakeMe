package com.takeme.services;

import com.takeme.models.User;
import com.takeme.models.UserToken;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class GetUserDetailsTask implements Callback<User> {

    private UserGetDetailsResponse userGetDetailsResponse;
    private Long token;

    public GetUserDetailsTask(Long token , UserGetDetailsResponse userGetDetailsResponse){
        this.userGetDetailsResponse = userGetDetailsResponse;
        this.token = token;
    }

    public void getUserDetails(){
        Call<User> call = TakeMeRestClient.getInstance().service().getUser(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<User> response) {

        if (response.isSuccess()){
            userGetDetailsResponse.onUserGetDetailsSuccess(response.body());
        }
        else{
            userGetDetailsResponse.onUserGetDetailsFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        userGetDetailsResponse.onRestCallError(t);
    }


    public interface UserGetDetailsResponse {

        public void onUserGetDetailsSuccess(User user);

        public void onUserGetDetailsFailed();

        public void onRestCallError(Throwable t);
    }

}
