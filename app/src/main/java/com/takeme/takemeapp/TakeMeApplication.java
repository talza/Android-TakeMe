package com.takeme.takemeapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import com.takeme.services.AwsS3Provider;
import com.takeme.services.TakeMeUtil;

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

    private static Context context;
    private ProgressDialog progress;

    private Long currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        progress = new ProgressDialog(this.context);

        AwsS3Provider.getInstance().init(context);
        TakeMeUtil.getInstance().init(context);
    }

    public static Context getContext(){
        return context;
    }


    public Long  getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Long currentUser) {
        this.currentUser = currentUser;
    }

    public void showProgress(Context context){
        progress = ProgressDialog.show(context, null,
                "In Progress...", true);
    }

    public void hideProgress(){
        if(progress != null) {
            progress.dismiss();
        }
    }

}

