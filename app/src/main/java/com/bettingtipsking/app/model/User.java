package com.bettingtipsking.app.model;

public class User {

    private String uid;
    private String name;
    private String email;
    private String mobile_number;
    private String image;
    private String password;
    private String subscription_date;
    private String subscription_package;

    public User(String uid, String name, String email, String mobile_number, String image, String password, String subscription_date, String subscription_package) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.image = image;
        this.password = password;
        this.subscription_date = subscription_date;
        this.subscription_package = subscription_package;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubscription_date() {
        return subscription_date;
    }

    public void setSubscription_date(String subscription_date) {
        this.subscription_date = subscription_date;
    }

    public String getSubscription_package() {
        return subscription_package;
    }

    public void setSubscription_package(String subscription_package) {
        this.subscription_package = subscription_package;
    }
}
