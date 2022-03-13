package com.bettingtipsking.app.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Predictions.class,PendingPredictions.class}, version = 2)
public abstract class MyLocalDatabase extends RoomDatabase {
    private static MyLocalDatabase localDatabase;

    public abstract PredictionsDao predictionsDao();

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

