package com.bettingtipsking.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.room.dao.MatchesNotificationDao;
import com.bettingtipsking.app.room.database.MyLocalDatabase;
import com.bettingtipsking.app.room.entity.MatchesNotificationEntity;

import java.util.List;

public class MatchesNotificationRepository {

    private MyLocalDatabase database;
    private MatchesNotificationDao notificationDao;
    private LiveData<List<MatchesNotificationEntity>> matchesNotificationLiveData;

    public MatchesNotificationRepository(Application application) {

        database = MyLocalDatabase.getInstance(application);
        notificationDao = database.matchesNotificationDao();
        matchesNotificationLiveData = new MutableLiveData<>();
        matchesNotificationLiveData = notificationDao.getMatchesNotificationEntity();
    }


    public long insertMatchNotification(MatchesNotificationEntity matchesNotificationEntity) {
        try {
            return new InsertMatchesNotification(notificationDao).execute(matchesNotificationEntity).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception call " + e.getMessage());
            return -1;
        }
    }

    public long deleteNotifiedMatches(MatchesNotificationEntity notificationEntity) {
        try {
            return new DeleteNotifiedMatches(notificationDao).execute(notificationEntity).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception call " + e.getMessage());
            return -1;
        }
    }


    public LiveData<List<MatchesNotificationEntity>> getAllFavouriteLeagues() {
        return matchesNotificationLiveData;
    }

    private class InsertMatchesNotification extends AsyncTask<MatchesNotificationEntity, Void, Long> {
        private MatchesNotificationDao notificationDao;

        private InsertMatchesNotification(MatchesNotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }

        @Override
        protected Long doInBackground(MatchesNotificationEntity... notificationEntities) {
            long key = notificationDao.insertMatch(notificationEntities[0]);
            return key;
        }
    }


    private class DeleteNotifiedMatches extends AsyncTask<MatchesNotificationEntity, Void, Integer> {
        private MatchesNotificationDao notificationDao;

        private DeleteNotifiedMatches(MatchesNotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }

        @Override
        protected Integer doInBackground(MatchesNotificationEntity... notificationEntities) {
            int key = notificationDao.deleteFavouritLeague(notificationEntities[0]);
            return key;
        }
    }


}
