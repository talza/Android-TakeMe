package com.takeme.services;


import com.takeme.models.PetAdd2Wishlist;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetAdd2WishListTask implements Callback<Object> {

    private Long token;
    private PetAdd2Wishlist petAdd2Wishlist;

    public PetAdd2WishListTask(Long token, Long petId){
        this.token = token;
        this.petAdd2Wishlist = new PetAdd2Wishlist();
        this.petAdd2Wishlist.setAdId(petId);
    }

    public void add2WishList(){

        Call<Object> call = TakeMeRestClient.getInstance().service().addPetAd2WishList(this.token,this.petAdd2Wishlist);
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
