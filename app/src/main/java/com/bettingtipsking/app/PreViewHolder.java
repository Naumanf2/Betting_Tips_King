package com.bettingtipsking.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bettingtipsking.app.repository.Repository;
import com.bettingtipsking.app.Room.PendingPredictions;
import com.bettingtipsking.app.Room.Predictions;

import java.util.List;

public class PreViewHolder extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Predictions>> liveData;

    public PreViewHolder(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        liveData = repository.getAllPredictions();
    }


    public LiveData<List<Predictions>> getAllpredictions() {
        return liveData;
    }

    public long insert(Predictions predictions) {
        return repository.insert(predictions);
    }

    public void deleteAllPerdictions(Predictions predictions) {
        repository.deleteAllPerdictions(predictions);
    }

    public void deleteSpecificPerdictions(Predictions predictions) {
        repository.deleteSpecificPerdictions(predictions);
    }


    public long insertPendingPredictions(PendingPredictions pendingPredictions) {
        return repository.insertPendingPredictions(pendingPredictions);
    }

    public List<PendingPredictions> getPendingPredictionsBaseONDate(String date) {
        return repository.getPendingPredictionsBaseONDate(date);
    }

}
