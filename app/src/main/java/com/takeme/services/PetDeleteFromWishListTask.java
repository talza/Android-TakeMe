package com.takeme.services;

import com.takeme.models.PetAdd2Wishlist;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetDeleteFromWishListTask implements Callback<Object> {

    private Long token;
    private Long petId;

    public PetDeleteFromWishListTask(Long token, Long petId){
        this.token = token;
        this.petId = petId;
    }

    public void deleteFromWishList(){

        Call<Object> call = TakeMeRestClient.getInstance().service().deletePetAdFromWishList(this.token,this.petId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<Object> response) {

        if (response.isSuccess()){
            // Do nothing
        }
        else{
            // Do nothing
        }

    }

    @Override
    public void onFailure(Throwable t) {
        // Do nothing
    }

}
