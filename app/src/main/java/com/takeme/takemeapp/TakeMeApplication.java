package com.takeme.takemeapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.takeme.services.AwsS3Provider;
import com.takeme.services.RegistrationDeviceUtil;
import com.takeme.services.TakeMeUtil;

import java.io.IOException;

public class TakeMeApplication extends Application { //implements GetAdsUserLikesListener{

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
        RegistrationDeviceUtil.getInstance().init(context);

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
        if(progress == null) {
            progress = ProgressDialog.show(context, null,
                    "Loading...", true);
        }
    }

    public void hideProgress(){
        if(progress != null) {
            progress.dismiss();
            progress = null;
        }
    }


}

