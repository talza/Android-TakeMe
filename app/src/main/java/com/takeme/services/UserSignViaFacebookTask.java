package com.takeme.services;

import com.takeme.models.User;
import com.takeme.models.UserToken;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class UserSignViaFacebookTask implements Callback<UserToken> {

    private UserSignViaFacebookResponse userSignViaFacebookResponse;
    private User user;

    public UserSignViaFacebookTask(String email, String firstName, String lastName, String phoneNumber,String facebookToken, UserSignViaFacebookResponse userSignViaFacebookResponse){
        this.userSignViaFacebookResponse = userSignViaFacebookResponse;
        user = new User();
        user.setEmail(email);
        user.setPassword(null);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setFacebookToken(facebookToken);
    }

    public void signViaFacebook(){
        Call<UserToken> call = TakeMeRestClient.getInstance().service().signViaFacebook(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<UserToken> response) {

        if (response.isSuccess()){
            userSignViaFacebookResponse.onRegisterSuccess(response.body());
        }
        else{
            userSignViaFacebookResponse.onRegisterFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        userSignViaFacebookResponse.onRestCallError(t);
    }


    public interface UserSignViaFacebookResponse {

        public void onRegisterSuccess(UserToken id);

        public void onRegisterFailed();

        public void onRestCallError(Throwable t);
    }

}
