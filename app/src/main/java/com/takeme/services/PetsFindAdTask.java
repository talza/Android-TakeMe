package com.takeme.services;

import android.text.TextUtils;

import com.takeme.models.Pet;
import com.takeme.models.User;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;


public class PetsFindAdTask implements Callback<List<Pet>> {

    private PetsGetListResponse petsGetListResponse;
    private Long token;
    private int type;
    private int size;
    private int ageFrom;
    private int ageTo;
    private int gender;
    private boolean wishList;

    public PetsFindAdTask(Long token, Integer type, int size, int ageFrom, int ageTo, int gender, boolean wishList, PetsGetListResponse petsGetListResponse){
        this.petsGetListResponse = petsGetListResponse;

        this.token = token;
        this.type = type;
        this.size = size;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = gender;
        this.wishList = wishList;
    }

    public void getPetsList(){
        HashMap<String,String> hmFilter = new HashMap<>();

        if(this.token != null && this.token.intValue() != 0) {
            hmFilter.put("userId",this.token.toString());
        }

        if (this.type > 0) {
            hmFilter.put("petType", String.valueOf(this.type));
        }

        if (this.size > 0 ) {
            hmFilter.put("petSize", String.valueOf(this.size));
        }

        if(this.ageFrom > 0) {
            hmFilter.put("ageFrom", String.valueOf(this.ageFrom));
        }

        if(this.ageTo > 0){
            hmFilter.put("ageTo", String.valueOf(this.ageTo));
        }
         if(this.gender > 0)  {
             hmFilter.put("petGender", String.valueOf(this.gender));
         }

         if(this.wishList) {
             hmFilter.put("inWishList", String.valueOf(this.wishList));
         }

        Call<List<Pet>> call =
                TakeMeRestClient.getInstance().service().findPets(hmFilter);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<List<Pet>> response) {

        if (response.isSuccess()){
            petsGetListResponse.onPetsGetListSuccess(response.body());
        }
        else{
            petsGetListResponse.onPetsGetListFailed();
        }

    }

    @Override
    public void onFailure(Throwable t) {
        petsGetListResponse.onRestCallError(t);
    }


    public interface PetsGetListResponse {

        public void onPetsGetListSuccess(List<Pet> lsPets);

        public void onPetsGetListFailed();

        public void onRestCallError(Throwable t);
    }

}
