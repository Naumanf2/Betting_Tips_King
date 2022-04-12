package com.bettingtipsking.app.model;

import com.bettingtipsking.app.model.league.Response;

import java.util.ArrayList;
import java.util.List;

public class FavouritLeagueModel {
    public int id;
    String name;
    String type;
    String logo;
    String country;
    int favStatus;

    public FavouritLeagueModel(int id, String name, String type, String logo, String country, int favStatus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.logo = logo;
        this.country = country;
        this.favStatus = favStatus;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(int favStatus) {
        this.favStatus = favStatus;
    }
}
