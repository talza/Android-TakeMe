package com.takeme.models;

/**
 * Created by tzamir on 10/30/2015.
 */
public class PetOwner {
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;

    public PetOwner() {
    }

    public PetOwner(String ownerName, String ownerEmail, String ownerPhone) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}
