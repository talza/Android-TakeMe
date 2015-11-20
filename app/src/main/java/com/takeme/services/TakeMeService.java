package com.takeme.services;

import com.takeme.models.PetAdd2Wishlist;
import com.takeme.models.Pet;
import com.takeme.models.UserToken;
import com.takeme.models.User;
import com.takeme.models.UserLogin;

import java.util.List;
import java.util.Map;

import retrofit.Call;
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
    Call<Pet> findPetById(@Path("id") Long petId, @Query("userId") Long token);

    @POST("/ad")
    Call<PostActionResponse> createPetAd(@Body Pet pet,@Query("userId") Long token);

    @PUT("/ad/{id}")
    Call<Object> updatePetAd(@Path("id") Long petId,@Body Pet pet,@Query("userId") Long token);

    @DELETE("/ad/{id}")
    Call<Object> deletePetAd(@Path("id") Long petId,@Query("userId") Long token);

    @POST("user/{userId}/wishlist")
    Call<Object> addPetAd2WishList(@Path("userId") Long token, @Body PetAdd2Wishlist petAdd2Wishlist);

    @DELETE("user/{userId}/wishlist/{petId}")
    Call<Object> deletePetAdFromWishList(@Path("userId") Long token, @Path("petId") Long petId);

}
