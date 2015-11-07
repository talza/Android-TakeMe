package com.takeme.services;

import com.takeme.models.Pet;
import com.takeme.models.UserToken;
import com.takeme.models.User;
import com.takeme.models.UserLogin;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;


public interface TakeMeService {

    @POST("user/signIn")
    Call<UserToken> signIn(@Body UserLogin login);

    @POST("user/signUp")
    Call<UserToken> signUp(@Body User user);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") Long token);

    @PUT("user/{id}")
    Call<PostActionResponse> updateUser(@Path("id") Long token ,@Body User user);

    @GET("/ad")
    Call<List<Pet>> findPets(@QueryMap Map<String,String> filters);

    @GET("/ad/{id}")
    Call<List<Pet>> findPetById(@Query("userId") Long token, @Path("id") Long petId);

    @POST("/ad")
    Call<PostActionResponse> createPetAd(@Query("userId") Long token, @Body Pet pet);

    @DELETE("/ad/{id}")
    Call<PostActionResponse> deletePetAd(@Query("userId") Long token, @Path("id") Long petId);

    @PUT("/ad/{id}")
    Call<PostActionResponse> updatePetAd(@Query("userId") Long token, @Path("id") Long petId, @Body Pet pet);



}
