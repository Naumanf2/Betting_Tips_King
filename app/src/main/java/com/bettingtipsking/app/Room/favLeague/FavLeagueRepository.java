package com.bettingtipsking.app.Room.favLeague;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Room.MyLocalDatabase;
import com.bettingtipsking.app.repository.Repository;

import java.util.List;

public class FavLeagueRepository {
    private FavLeaguesDao favLeaguesDao;
    private LiveData<List<FavLeagues>> favLeagueList = new MutableLiveData<>();

    public FavLeagueRepository(Application application) {
        MyLocalDatabase database = MyLocalDatabase.getInstance(application);

        favLeaguesDao = database.favLeaguesDao();
        favLeagueList = favLeaguesDao.getAllFavLeagues();

    }
    public long insertFavLeague(FavLeagues favLeagues) {
        try {
            return new FavLeagueRepository.InsertLeagueAsyncTask(favLeaguesDao).execute(favLeagues).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Expection call " + e.getMessage());
            return -1;
        }
    }
    public LiveData<List<FavLeagues>> getAllFavLeagues() {
        return favLeagueList;
    }
    public LiveData<List<FavLeagues>> getFavLeagueById(int id) {
        return favLeagueList;
    }

    private static class InsertLeagueAsyncTask extends AsyncTask<FavLeagues, Void, Long> {
        private FavLeaguesDao favLeaguesDao;

        private InsertLeagueAsyncTask(FavLeaguesDao favLeaguesDao) {
            this.favLeaguesDao = favLeaguesDao;
        }
        @Override
        protected Long doInBackground(FavLeagues... favLeagues) {
            long key = favLeaguesDao.insertFavLeague(favLeagues[0]);
            return key;
        }
    }


}
