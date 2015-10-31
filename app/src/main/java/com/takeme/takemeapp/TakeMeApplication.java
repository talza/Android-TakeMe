package com.takeme.takemeapp;

import android.app.Application;
import android.content.Context;

public class TakeMeApplication extends Application { //implements GetAdsUserLikesListener{

    public static final String AGE = "Age";
    public static final String SIZE = "Size";
    public static final String ANIMAL = "Animal";
    public static final String GENDER = "Gender";
    public static final String FROM_AGE = "FromAge";
    public static final String TO_AGE = "ToAge";
    public static final String USER = "user";
    public static final String FAV = "fav";

    public static final String LOGIN_PARAM = "login_param";
    public static final int LOGIN = 1;
    public static final int SIGN_UP = 2;

    //public PetListProvider petListProvider = new PetListProvider();

    private static Context context;

   // private S3Provider s3Provider;

    private Long currentUser;

   // private List<AdResponseItem> userFavs = new ArrayList<AdResponseItem>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

      //  s3Provider = new S3Provider(context);
    }

    public static Context getContext(){
        return context;
    }

//    public void getFileForS3Key(String key, FileDownloadCallBack fileDownloadCallBack){
//        s3Provider.getFileForKey(key,fileDownloadCallBack);
//    }
//
//    public void uploadImageToS3(String fileName,File file, FileUploadCallBack fileUploadCallBack){
//        s3Provider.uploadImage(fileName,file,fileUploadCallBack);
//    }
//
//    public String getPicUrl(UploadResult uploadResult){
//        return s3Provider.getPicUrl(uploadResult);
//    }

    public Long  getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Long currentUser) {

        this.currentUser = currentUser;
//        refreshUserLikes();
    }

//    public void refreshUserLikes(){
//
//        GetAdsUserLikesTask getAdsUserLikesTask = new GetAdsUserLikesTask(this,currentUser);
//        getAdsUserLikesTask.getLikes();
//    }

//    public List<AdResponseItem> getUserFavs() {
//        return userFavs;
//    }

//    public boolean isOnFavList(String id){
//
//        for (AdResponseItem adResponseItem: userFavs){
//            if (Integer.toString(adResponseItem.getId()).equals(id))
//                return true;
//        }
//
//        return false;
//    }

//    @Override
//    public void onGetAdsUserLikesSuccess(List<AdResponseItem> likes) {
//        userFavs = likes;
//    }
//
//    @Override
//    public void onRestCallError(RetrofitError error) {
//
//    }
}

