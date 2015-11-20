package com.takeme.services;

import com.takeme.models.Pet;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetDeleteAdTask implements Callback<Object> {

    private PetDeleteAdResponse petDeleteAdResponse;
    private Long token;
    private Long petId;

    public PetDeleteAdTask(Long token, Long petId, PetDeleteAdResponse petDeleteAdResponse){
        this.petDeleteAdResponse = petDeleteAdResponse;

        this.token = token;
        this.petId = petId;
    }

    public void deletePetAd(){

        Call<Object> call = TakeMeRestClient.getInstance().service().deletePetAd(this.petId,this.token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<Object> response) {

        if (response.isSuccess()){
            petDeleteAdResponse.onPetDeleteAdSuccess();
        }
        else{
            petDeleteAdResponse.onPetDeleteAdFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        petDeleteAdResponse.onRestCallError(t);
    }


    public interface PetDeleteAdResponse {

        public void onPetDeleteAdSuccess();

        public void onPetDeleteAdFailed();

        public void onRestCallError(Throwable t);
    }

}
