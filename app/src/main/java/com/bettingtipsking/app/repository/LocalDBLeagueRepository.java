package com.bettingtipsking.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Room.database.MyLocalDatabase;
import com.bettingtipsking.app.Room.entity.FavouritLeaguesEntity;
import com.bettingtipsking.app.Room.dao.FavouritLeaguesDao;

import java.util.List;

public class LocalDBLeagueRepository {
    private MyLocalDatabase database;
    private FavouritLeaguesDao favouritLeaguesDao;
    private LiveData<List<FavouritLeaguesEntity>> favLeagueListLiveData;

    public LocalDBLeagueRepository(Application application) {
        database = MyLocalDatabase.getInstance(application);
        favouritLeaguesDao = database.favouritLeaguesDao();
        favLeagueListLiveData = new MutableLiveData<>();
        favLeagueListLiveData = favouritLeaguesDao.getAllFavouritLeagues();
    }

    public long insertFavouritLeague(FavouritLeaguesEntity favouritLeaguesEntity) {
        try {
            return new InsertLeagueAsyncTask(favouritLeaguesDao).execute(favouritLeaguesEntity).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception call " + e.getMessage());
            return -1;
        }
    }

    public LiveData<List<FavouritLeaguesEntity>> getAllFavouritLeagues() {
        return favLeagueListLiveData;
    }

    public long deleteFavouritLeague(FavouritLeaguesEntity favouritLeaguesEntity) {
        try {
            return new DeleteLeagueAsyncTask(favouritLeaguesDao).execute(favouritLeaguesEntity).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception call " + e.getMessage());
            return -1;
        }
    }

    private  class InsertLeagueAsyncTask extends AsyncTask<FavouritLeaguesEntity, Void, Long> {
        private FavouritLeaguesDao favouritLeaguesDao;

        private InsertLeagueAsyncTask(FavouritLeaguesDao favouritLeaguesDao) {
            this.favouritLeaguesDao = favouritLeaguesDao;
        }

        @Override
        protected Long doInBackground(FavouritLeaguesEntity... favLeagues) {
            long key = favouritLeaguesDao.insertFavouritLeague(favLeagues[0]);
            return key;
        }
    }

    private  class DeleteLeagueAsyncTask extends AsyncTask<FavouritLeaguesEntity, Void, Integer> {
        private FavouritLeaguesDao favouritLeaguesDao;

        private DeleteLeagueAsyncTask(FavouritLeaguesDao favouritLeaguesDao) {
            this.favouritLeaguesDao = favouritLeaguesDao;
        }

        @Override
        protected Integer doInBackground(FavouritLeaguesEntity... favLeagues) {
            int key = favouritLeaguesDao.deleteFavouritLeague(favLeagues[0]);
            return key;
        }
    }

}
