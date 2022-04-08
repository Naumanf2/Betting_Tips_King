package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bettingtipsking.app.repository.LineupRepository;

import java.util.List;

public class LineupViewModel extends AndroidViewModel {

    LineupRepository repository;
    MutableLiveData<List<TeamWithPlayers>> mutableLiveData;

    public MutableLiveData<List<TeamWithPlayers>> getMutableLiveData() {
        return mutableLiveData;
    }

    public LineupViewModel(@NonNull Application application) {
        super(application);
        repository = new LineupRepository(application);
        mutableLiveData = repository.getMutableLiveData();
    }

    public void getLineup(String url){
        repository.getLineup(url);
    }
}
