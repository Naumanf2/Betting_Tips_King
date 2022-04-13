package com.bettingtipsking.app.room.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MatchesNotificationEntity {

    @PrimaryKey
    public int id;
    String name;
    String logo;

    public MatchesNotificationEntity(int id, String name, String logo) {
        this.id = id;
        this.name = name;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}