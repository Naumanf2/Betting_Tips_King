package com.bettingtipsking.app.model;

public class Users {
    private String name,email, password, uid, subscription_date, subscription_package;

    public Users(String name, String email, String password, String uid, String subscription_date, String subscription_package) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.subscription_date = subscription_date;
        this.subscription_package = subscription_package;
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

    public Users(){}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}




