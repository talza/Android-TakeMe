package com.takeme.models;

import com.takeme.services.TakeMeUtil;

/**
 * Created by tzamir on 9/13/2015.
 * This class represent a model of pet ad
 */
public class Pet
{
    private Long id;
    private String petName;
    private Integer petGender;
    private Integer petAge;
    private Integer petSize;
    private Integer petType;
    private String petPhotoUrl;
    private String petDescription;
    private boolean inWishlist;
    private PetOwner petOwner;

    public Pet() {

    }

    public Pet(Long id, String petName, Integer petGender, Integer petAge, Integer petSize, Integer petType, String petPhotoUrl, String petDescription, boolean isWishInList, PetOwner petOwner) {
        this.id = id;
        this.petName = petName;
        this.petGender = petGender;
        this.petAge = petAge;
        this.petSize = petSize;
        this.petType = petType;
        this.petPhotoUrl = petPhotoUrl;
        this.petDescription = petDescription;
        this.inWishlist = isWishInList;
        this.petOwner = petOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getPetAge() {
        return petAge;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }

    public Integer getPetGender() {
        return petGender;
    }

    public void setPetGender(Integer petGender) {
        this.petGender = petGender;
    }

    public Integer getPetSize() {
        return petSize;
    }

    public void setPetSize(Integer petSize) {
        this.petSize = petSize;
    }
    public Integer getPetType() {
        return this.petType;
    }

    public void setPetType(Integer petType){
        this.petType = petType;
    }

    public String getPetPhotoUrl() {
        return petPhotoUrl;
    }

    public void setPetPhotoUrl(String petPhotoUrl) {
        this.petPhotoUrl = petPhotoUrl;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public boolean isInWishlist() {
        return inWishlist;
    }

    public void setIsWishInList(boolean isWishInList) {
        this.inWishlist = isWishInList;
    }

    public String getShortDescription(){

        return TakeMeUtil.getInstance().getGenderByIndex(Integer.valueOf(this.petGender)) +", "+ this.petAge;
    }

}
