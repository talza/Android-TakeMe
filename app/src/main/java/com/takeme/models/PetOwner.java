package com.takeme.models;

/**
 * Created by tzamir on 10/30/2015.
 */
public class PetOwner {
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerEmail;
    private String ownerPhone;

    public PetOwner() {
    }

    public PetOwner(String ownerFirstName, String ownerEmail, String ownerPhone) {
        this.ownerFirstName = ownerFirstName;
        this.ownerEmail = ownerEmail;
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
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
