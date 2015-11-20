package com.takeme.services;

import com.takeme.models.Pet;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetUpdateAdTask implements Callback<Object> {

    private PetUpdateAdResponse petUpdateAdResponse;
    private Long token;
    private Pet pet;

    public PetUpdateAdTask(Long token, Pet pet, PetUpdateAdResponse petUpdateAdResponse){
        this.petUpdateAdResponse = petUpdateAdResponse;

        this.token = token;
        this.pet = pet;
    }

    public void updatePetAd(){

        Call<Object> call = TakeMeRestClient.getInstance().service().updatePetAd(this.pet.getId(),this.pet, this.token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<Object> response) {

        if (response.isSuccess()){
            petUpdateAdResponse.onPetUpdateAdSuccess();
        }
        else{
            petUpdateAdResponse.onPetUpdateAdFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        petUpdateAdResponse.onRestCallError(t);
    }


    public interface PetUpdateAdResponse {

        public void onPetUpdateAdSuccess();

        public void onPetUpdateAdFailed();

        public void onRestCallError(Throwable t);
    }

}
