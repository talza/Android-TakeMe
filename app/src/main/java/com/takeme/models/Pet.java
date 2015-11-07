package com.takeme.models;

import com.takeme.services.TakeMeUtil;

/**
 * Created by tzamir on 9/13/2015.
 */
public class Pet
{
    private String id;
    private String petName;
    private String petGender;
    private String petAge;
    private String petSize;
    private String petType;
    private String petPhotoUrl;
    private String petDescription;
    private boolean isWishInList;
    private PetOwner ownerDetails;

    public Pet() {

    }

    public Pet(String id, String petName, String petGender, String petAge, String petSize,
               String petType, String petPhotoUrl, String petDescription, boolean isWishInList,
               PetOwner ownerDetails) {
        this.id = id;
        this.petName = petName;
        this.petGender = petGender;
        this.petAge = petAge;
        this.petSize = petSize;
        this.petType = petType;
        this.petPhotoUrl = petPhotoUrl;
        this.petDescription = petDescription;
        this.isWishInList = isWishInList;
        this.ownerDetails = ownerDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }
    public String getPetType() {
        return this.petType;
    }

    public void setPetType(String petType){
        this.petType = petType;
    }

    public String getPetPhotoUrl() {
        return petPhotoUrl;
    }

    public void setPetPhotoUrl(String petPhotoUrl) {
        this.petPhotoUrl = petPhotoUrl;
    }

    public PetOwner getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(PetOwner ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public boolean isWishInList() {
        return isWishInList;
    }

    public void setIsWishInList(boolean isWishInList) {
        this.isWishInList = isWishInList;
    }

    public String getShortDescription(){

        return TakeMeUtil.getInstance().getGenderByIndex(Integer.valueOf(this.petGender)) +", "+ this.petAge;
    }

}
