package com.takeme.models;

/**
 * Created by tzamir on 9/13/2015.
 */
public class Pet
{
    private String id;
    private String name;
    private String description;
    private String age;
    private String gender;
    private String size;
    private String imgUrl;
    private boolean isWishInList;
    private String user;

    public Pet()
    {

    }

    public Pet(String id, String name, String description, String age, String gender, String size, String imgUrl, String user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.imgUrl = imgUrl;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isWishInList() {
        return isWishInList;
    }

    public void setIsWishInList(boolean isWishInList) {
        this.isWishInList = isWishInList;
    }

    public String getShortDescription(){ return this.gender +", "+ this.age; }

}
