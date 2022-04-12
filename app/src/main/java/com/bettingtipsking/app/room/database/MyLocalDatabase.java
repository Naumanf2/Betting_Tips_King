package com.bettingtipsking.app.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bettingtipsking.app.room.PendingPredictions;
import com.bettingtipsking.app.room.Predictions;
import com.bettingtipsking.app.room.PredictionsDao;
import com.bettingtipsking.app.room.entity.FavouritLeaguesEntity;
import com.bettingtipsking.app.room.dao.FavouritLeaguesDao;

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

