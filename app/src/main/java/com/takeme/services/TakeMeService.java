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

/**
 * This interface represent the REST Api of TakeMe server.
 */
public interface TakeMeService {

    /***
     * Sign in to application
     * @param login - Email and password
     * @return
     */
    @POST("user/signIn")
    Call<UserToken> signIn(@Body UserLogin login);

    /**
     * Sign up to application
     * @param user - User data
     * @return - User token
     */
    @POST("user/signUp")
    Call<UserToken> signUp(@Body User user);

    /**
     * Sign via facebook
     * @param user - User data
     * @return - User token
     */
    @POST("user/signViaFacebook")
    Call<UserToken> signViaFacebook(@Body User user);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") Long token);

    /**
     * Get user token by facebook token
     * @param facebookToken
     * @return -  User token
     */
    @GET("/user")
    Call<UserToken> getUserByFacbook(@Query("facebookToken") String facebookToken);

    /**
     * Update user data
     * @param token - user token
     * @param user - user data
     * @return
     */
    @PUT("user/{id}")
    Call<PostActionResponse> updateUser(@Path("id") Long token ,@Body User user);

    /**
     * Find pets by filters
     * @param filters - filters ( e.g age from, age to , size , type etc..)
     * @return - Pets list
     */
    @GET("ad")
    Call<List<Pet>> findPets(@QueryMap Map<String,String> filters);

    /**
     * Find pet by id
     * @param petId - Pet id
     * @param token - User token
     * @return  - Pet data
     */
    @GET("ad/{id}")
    Call<Pet> findPetById(@Path("id") Long petId, @Query("userId") Long token);

    /**
     * Create new pet
     * @param pet - Pet data
     * @param token - user token
     * @return
     */
    @POST("ad")
    Call<PostActionResponse> createPetAd(@Body Pet pet,@Query("userId") Long token);

    /**
     * Update pet data
     * @param petId - Pet id
     * @param pet - Pet data
     * @param token - User token
     * @return
     */
    @PUT("ad/{id}")
    Call<Object> updatePetAd(@Path("id") Long petId,@Body Pet pet,@Query("userId") Long token);

    /**
     * Delete pet
     * @param petId - Pet id
     * @param token - User token
     * @return
     */
    @DELETE("ad/{id}")
    Call<Object> deletePetAd(@Path("id") Long petId,@Query("userId") Long token);

    /**
     * Add pet to wishlist
     * @param token - User token
     * @param petAdd2Wishlist pet id
     * @return
     */
    @POST("ad/{userId}/wishlist")
    Call<Object> addPetAd2WishList(@Path("userId") Long token, @Body PetAdd2Wishlist petAdd2Wishlist);

    /**
     * Delete pet from wishlist
     * @param token - User token
     * @param petId - Pet id
     * @return
     */
    @DELETE("ad/{userId}/wishlist/{petId}")
    Call<Object> deletePetAdFromWishList(@Path("userId") Long token, @Path("petId") Long petId);

}
