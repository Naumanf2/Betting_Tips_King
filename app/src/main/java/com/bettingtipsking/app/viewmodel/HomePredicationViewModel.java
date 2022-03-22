package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bettingtipsking.app.repository.HomePredicationRepository;

public class HomePredicationViewModel extends AndroidViewModel {

    HomePredicationRepository repository;

    public HomePredicationViewModel(@NonNull Application application) {
        super(application);
        repository = new HomePredicationRepository(application);
    }

    public void getAllPredictions(String url){
        repository.getAllPredictions(url);
    }
}
