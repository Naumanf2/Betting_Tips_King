package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bettingtipsking.app.repository.Repository;

public class FavLeagueViewModel extends AndroidViewModel {
    Repository repository;


    public FavLeagueViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

    }




}
