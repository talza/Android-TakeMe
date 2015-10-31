package com.takeme.services;


import com.takeme.models.User;


import retrofit.Callback;
import retrofit.Response;

/**
 * Created by tzamir on 10/19/2015.
 */
public class UserUpdateTask implements Callback<PostActionResponse> {

    private UserUpdateResponse userUpdateResponse;
    private User user;
    private Long id;


    public UserUpdateTask(Long id, String firstName, String lastName, String phoneNum,UserUpdateResponse userUpdateResponse) {
        this.userUpdateResponse = userUpdateResponse;
        this.id = id;
        user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNum);
    }

    public void updateUser(){

        retrofit.Call<PostActionResponse> call = TakeMeRestClient.getInstance().service().updateUser(this.id, this.user);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Response<PostActionResponse> postActionResponse) {

        if (postActionResponse.isSuccess()){
            userUpdateResponse.onUpdateSuccess(postActionResponse.body());
        }else {
            userUpdateResponse.onUpdateFailed(postActionResponse.body());
        }

    }

    @Override
    public void onFailure(Throwable t) {
        userUpdateResponse.onRestCallError(t);
    }

    public interface UserUpdateResponse {

        public void onUpdateSuccess(PostActionResponse PostActionResponse);

        public void onUpdateFailed(PostActionResponse PostActionResponse);

        public void onRestCallError(Throwable t);
    }
}



