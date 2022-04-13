package com.bettingtipsking.app.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pending_predictions_table")
public class PendingPredictions {

    @NonNull
    @PrimaryKey()
    int key;
    String match_date;
    String match_status;

    public PendingPredictions(int key, String match_date, String match_status) {
        this.key = key;
        this.match_date = match_date;
        this.match_status = match_status;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }
}
