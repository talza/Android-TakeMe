package com.takeme.services;

/**
 * Created by tzamir on 10/20/2015.
 */
public final class Constants {

    public static final int MAX_AGE = 20;
    public static final int MIN_AGE = 1;

    public static final int PET_UPDATE_MODE = 1;
    public static final int PET_NEW_MODE = 2;

    public static final String IMAGES_BUCKET_NAME = "takeme-bucket";
    public static final String IMAGES_PREFIX = "takeme-images/";

    public static final String PET_LIST_MODE = "PET_LIST_MODE";

    public enum PetsListMode {
        PetsList, MyPets, WishList
    }

}
