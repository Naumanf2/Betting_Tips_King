package com.bettingtipsking.app.Room.favLeague;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavLeaguesDao {
    @Insert
    long insertFavLeague(FavLeagues leagues);

    @Query("SELECT * FROM favleagues")
    LiveData<List<FavLeagues>> getAllFavLeagues();

    @Query("SELECT * FROM favleagues WHERE id = :id")
    LiveData<List<FavLeagues>> getFavLeagueById(int id);
}
