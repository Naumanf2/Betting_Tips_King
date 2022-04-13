package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.model.HomelPredictionsModel;
import com.bettingtipsking.app.repository.HomePredicationRepository;

import java.util.List;

public class HomePredicationViewModel extends AndroidViewModel {

    HomePredicationRepository repository;
    MutableLiveData<List<HomelPredictionsModel>> listMutableLiveData;

    public HomePredicationViewModel(@NonNull Application application) {
        super(application);
        repository = new HomePredicationRepository(application);
        listMutableLiveData = repository.getListMutableLiveData();
    }

    public MutableLiveData<List<HomelPredictionsModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void getAllPredictions(String url){
        repository.getAllPredictions(url);
    }
}
