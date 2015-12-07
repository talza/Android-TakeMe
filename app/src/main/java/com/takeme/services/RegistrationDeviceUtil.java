package com.takeme.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by tzamir on 11/30/2015.
 */
public class RegistrationDeviceUtil {

    private GoogleCloudMessaging gcm;
    private String regid;
    private Context context;

    public synchronized void init(Context context){
        this.context = context;
        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
        }
    }


    public void getRegId(GetRegistrationDeviceIdCallBack getRegistrationDeviceIdCallBack){
        if (regid == null) {
            synchronized (RegistrationDeviceUtil.class) {
                if (regid == null) {
                    new GetRegistrationDeviceIdTask(getRegistrationDeviceIdCallBack).execute(null, null, null);
                }
            }
        }else{
            getRegistrationDeviceIdCallBack.onGetRegistrationDeviceId(regid);
        }
    }

    /**
     * SingletonHolder is loaded on the first execution of RegistrationDeviceUtil.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private final static RegistrationDeviceUtil INSTANCE = new RegistrationDeviceUtil();
    }

    public static RegistrationDeviceUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public interface GetRegistrationDeviceIdCallBack{
        public void onGetRegistrationDeviceId(String regId);
    }

    private class GetRegistrationDeviceIdTask extends AsyncTask<Void, Void, String>{

        private GetRegistrationDeviceIdCallBack getRegistrationDeviceIdCallBack;
        private GetRegistrationDeviceIdTask(GetRegistrationDeviceIdCallBack getRegistrationDeviceIdCallBack){
            this.getRegistrationDeviceIdCallBack = getRegistrationDeviceIdCallBack;
        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                regid = gcm.register(Constants.PROJECT_NUMBER);
                msg = "Device registered, registration ID=" + regid;
                Log.i("GCM", msg);

            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();

            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            this.getRegistrationDeviceIdCallBack.onGetRegistrationDeviceId(regid);
        }
    }
}
