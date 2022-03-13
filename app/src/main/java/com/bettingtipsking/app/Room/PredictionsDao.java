package com.bettingtipsking.app.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PredictionsDao {

    @Insert
    long insertAlbum(Predictions predictions);

    @Query("SELECT * FROM predictions_table")
    LiveData<List<Predictions>> getAllPredictions();

    @Delete
    void delete(Predictions predictions);

    @Query("DELETE FROM predictions_table WHERE id = :tid")
    void deleteSpecifPredictions(int tid);


    @Insert
    long insertPendingPredictions(PendingPredictions pendingPredictions);


    @Query("SELECT * FROM pending_predictions_table WHERE match_date = :date")
    List<PendingPredictions> getPendingPredictionsBaseONDate(String date);

}
