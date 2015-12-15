package com.takeme.services;

/**
 * Created by tzamir on 10/20/2015.
 *
 * This class represent to constants of the application
 */
public final class Constants {

    public static final String PROJECT_NUMBER = "1071569535836";

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int MAX_AGE = 20;
    public static final int MIN_AGE = 1;

    public static final String IMAGES_BUCKET_NAME = "takeme-bucket";
    public static final String IMAGES_PREFIX = "takeme-images/";

    public static final String PET_LIST_MODE = "PET_LIST_MODE";
    public static final String PET_DETAILS_ID = "PET_DETAILS_ID";
    public static final String PET_UPDATE_MODE = "PET_UPDATE_MODE";

    public static final String PET_UPDATE_ID = "PET_UPDATE_ID";
    public static final String PET_UPDATE_NAME = "PET_UPDATE_NAME";
    public static final String PET_UPDATE_TYPE = "PET_UPDATE_TYPE";
    public static final String PET_UPDATE_SIZE = "PET_UPDATE_SIZE";
    public static final String PET_UPDATE_AGE = "PET_UPDATE_AGE";
    public static final String PET_UPDATE_GENDER = "PET_UPDATE_GENDER";
    public static final String PET_UPDATE_DESC = "PET_UPDATE_DESC";
    public static final String PET_UPDATE_PIC_URL = "PET_UPDATE_PIC_URL";

    public enum PetsListMode {
        PetsList, MyPets, WishList
    }

    public enum PetUpdateMode{
        CREATE, UPDATE
    }


}
