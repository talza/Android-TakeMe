package com.takeme.services;

import com.takeme.models.UserToken;
import com.takeme.models.User;
import com.takeme.models.UserLogin;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


public interface TakeMeService {

    @POST("user/signIn")
    Call<UserToken> signIn(@Body UserLogin login);

    @POST("user/signUp")
    Call<UserToken> signUp(@Body User user);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") Long token);

    @PUT("user/{id}")
    Callback<PostActionResponse> updateUser(@Path("id") Long token ,@Body User user);

//    //Query ads
//    @GET("/ad")
//    void getAd(@QueryMap Map<String, String> params,
//               Callback<List<AdResponseItem>> callback);
//
//    @GET("/user")
//    void getUser(@QueryMap Map<String, String> params, Callback<UserDetails> callback);
//
//    @POST("/user")
//    void updateUser(@QueryMap Map<String, String> params, Callback<PostActionResponse> callback);
//
//    @PUT("/ad")
//    void addAd(@Query("desc") String desc,
//               @Query("size") String size,
//               @Query("type") String type,
//               @Query("user") String user,
//               @Query("petName") String petName,
//               @Query("story") String story,
//               @Query("photoURL") String photoURL,
//               @Query("gender") String gender,
//               @Query("age") String age,
//               Callback<PostActionResponse> callback);
//
//    @DELETE("/ad")
//    void deleteAd(@Query("adID") String adID, Callback<PostActionResponse> callback);
//
//    @GET("/like")
//    void getLikesForAd(@Query("adID") String id, Callback<List<FavRespond>> callback);
//
//    @GET("/like")
//    void getLikesForUser(@Query("user") String user, Callback<List<AdResponseItem>> callback);
//
//    @PUT("/like")
//    void adLike(@Query("adID") String adID, @Query("user") String user, Callback<PostActionResponse> callback);
//
//    @DELETE("/like")
//    void deleteLike(@Query("adID") String adID, @Query("user") String user, Callback<PostActionResponse> callback);

}
