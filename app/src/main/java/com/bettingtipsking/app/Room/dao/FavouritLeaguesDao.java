package com.bettingtipsking.app.Room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bettingtipsking.app.Room.entity.FavouritLeaguesEntity;

import java.util.List;

@Dao
public interface FavouritLeaguesDao {



    @Insert
    long insertFavouritLeague(FavouritLeaguesEntity leagues);

    @Query("SELECT * FROM FavouritLeaguesEntity")
    LiveData<List<FavouritLeaguesEntity>> getAllFavouritLeagues();

    @Delete
    int deleteFavouritLeague(FavouritLeaguesEntity leagues);


}
