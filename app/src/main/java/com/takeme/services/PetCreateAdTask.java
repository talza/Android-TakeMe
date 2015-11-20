package com.takeme.services;

import com.takeme.models.Pet;

import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetCreateAdTask implements Callback<PostActionResponse> {

    private PetCreateAdResponse petCreateAdResponse;
    private Long token;
    private Pet pet;

    public PetCreateAdTask(Long token, Pet pet, PetCreateAdResponse petCreateAdResponse){
        this.petCreateAdResponse = petCreateAdResponse;

        this.token = token;
        this.pet = pet;
    }

    public void createPetAd(){

        Call<PostActionResponse> call = TakeMeRestClient.getInstance().service().createPetAd(this.pet,this.token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<PostActionResponse> response) {

        if (response.isSuccess()){
            petCreateAdResponse.onPetCreateAdSuccess();
        }
        else{
            petCreateAdResponse.onPetCreateAdFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        petCreateAdResponse.onRestCallError(t);
    }


    public interface PetCreateAdResponse {

        public void onPetCreateAdSuccess();

        public void onPetCreateAdFailed();

        public void onRestCallError(Throwable t);
    }

}
