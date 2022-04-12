package com.bettingtipsking.app.Room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bettingtipsking.app.Room.PendingPredictions;
import com.bettingtipsking.app.Room.Predictions;
import com.bettingtipsking.app.Room.PredictionsDao;
import com.bettingtipsking.app.Room.entity.FavouritLeaguesEntity;
import com.bettingtipsking.app.Room.dao.FavouritLeaguesDao;

@Database(entities = {Predictions.class, PendingPredictions.class, FavouritLeaguesEntity.class}, version = 2)
public abstract class MyLocalDatabase extends RoomDatabase {
    private static MyLocalDatabase localDatabase;

    public abstract PredictionsDao predictionsDao();
    public abstract FavouritLeaguesDao favouritLeaguesDao();

    public static synchronized MyLocalDatabase getInstance(Context context) {
        if (localDatabase == null) {
            localDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    MyLocalDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return localDatabase;
    }
}

