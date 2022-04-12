package com.bettingtipsking.app.Room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouritLeaguesEntity {
    @PrimaryKey
    public int id;
    String name;
    String type;
    String logo;

    public FavouritLeaguesEntity(int id, String name, String type, String logo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.logo = logo;
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
}
