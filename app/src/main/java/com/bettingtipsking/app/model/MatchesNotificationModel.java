package com.bettingtipsking.app.model;

public class MatchesNotificationModel {
    int id;
    String name;
    String logo;
    int status;

    public MatchesNotificationModel(int id, String name, String logo, int status) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
