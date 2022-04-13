package com.bettingtipsking.app.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bettingtipsking.app.room.entity.MatchesNotificationEntity;

import java.util.List;

@Dao
public interface MatchesNotificationDao {

    @Insert
    long insertMatch(MatchesNotificationEntity notificationEntity);

    @Query("SELECT * FROM MatchesNotificationEntity")
    LiveData<List<MatchesNotificationEntity>> getMatchesNotificationEntity();

    @Delete
    int deleteFavouritLeague(MatchesNotificationEntity notificationEntity);
}
