package com.takeme.services;

import com.squareup.okhttp.OkHttpClient;

import retrofit.Retrofit;

/* Singleton class to get the rest api to call any method. */
public class TakeMeRestClient {

    private TakeMeService takeMeService;
    private static String ROOT =
            "http://54.69.124.99:8080/Service/";

    /**
     * SingletonHolder is loaded on the first execution of TakeMeRestClient.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private final static TakeMeRestClient INSTANCE = new TakeMeRestClient();
    }

    public static TakeMeRestClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private TakeMeRestClient(){
        setupRestClient();
    };

    public TakeMeService get() {
        return takeMeService;
    }

    private void setupRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT)
                .build();

        takeMeService = retrofit.create(TakeMeService.class);
    }


}




