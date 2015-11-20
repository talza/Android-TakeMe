package com.takeme.services;

import com.takeme.models.Pet;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetGetAdTask implements Callback<Pet> {

    private PetGetAdResponse petGetAdResponse;
    private Long token;
    private Long petId;

    public PetGetAdTask(Long token, Long petId, PetGetAdResponse petGetAdResponse){
        this.petGetAdResponse = petGetAdResponse;

        this.token = token;
        this.petId = petId;
    }

    public void getPetAd(){

        Call<Pet> call = TakeMeRestClient.getInstance().service().findPetById(this.petId,this.token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<Pet> response) {

        if (response.isSuccess()){
            petGetAdResponse.onPetGetAdSuccess(response.body());
        }
        else{
            petGetAdResponse.onPetGetAdFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        petGetAdResponse.onRestCallError(t);
    }


    public interface PetGetAdResponse {

        public void onPetGetAdSuccess(Pet pet);

        public void onPetGetAdFailed();

        public void onRestCallError(Throwable t);
    }

}
