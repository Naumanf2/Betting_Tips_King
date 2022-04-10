package com.bettingtipsking.app.Room.favLeague;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FavLeagueViewModel extends AndroidViewModel {
    LiveData<List<FavLeagues>> favLeaguesLiveData = new MutableLiveData<>();
    FavLeagueRepository repository;
    public FavLeagueViewModel(@NonNull Application application) {
        super(application);
        repository = new FavLeagueRepository(application);
        favLeaguesLiveData = repository.getAllFavLeagues();

    }
    public void insertFavLeague(FavLeagues favLeagues){
        repository.insertFavLeague(favLeagues);
    }
    public LiveData<List<FavLeagues>> getFavLeagueById(int id){
        return repository.getFavLeagueById(id);
    }
    public LiveData<List<FavLeagues>> getFavLeague(){
       return repository.getAllFavLeagues();
    }
}
