package com.takeme.services;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/* Singleton class to get the rest api to call any method. */
public class TakeMeRestClient {

    private TakeMeService takeMeService;
    private static String ROOT =
            "https://take-me-server.herokuapp.com/";

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

    public TakeMeService service() {
        return takeMeService;
    }

    private void setupRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        takeMeService = retrofit.create(TakeMeService.class);
    }


}




