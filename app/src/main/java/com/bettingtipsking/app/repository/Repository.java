package com.bettingtipsking.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bettingtipsking.app.Room.MyLocalDatabase;
import com.bettingtipsking.app.Room.PendingPredictions;
import com.bettingtipsking.app.Room.Predictions;
import com.bettingtipsking.app.Room.PredictionsDao;

import java.util.List;

public class Repository {

    private PredictionsDao predictionsDao;
    private LiveData<List<Predictions>> liveData;

    public Repository(Application application) {
        MyLocalDatabase database = MyLocalDatabase.getInstance(application);
        predictionsDao = database.predictionsDao();
        liveData = predictionsDao.getAllPredictions();
    }

    public long insert(Predictions prediction) {
        try {
            return new InserPredictionAsyncTask(predictionsDao).execute(prediction).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Expection call " + e.getMessage());
            return -1;
        }
    }


    public LiveData<List<Predictions>> getAllPredictions() {
        return liveData;
    }

    public void deleteAllPerdictions(Predictions predictions) {
        new DetelePredictionAsyncTask(predictionsDao).execute(predictions);
    }

    public void deleteSpecificPerdictions(Predictions predictions) {
        new DeleteSpecificPreicationAsyncTask(predictionsDao).execute(predictions);
    }

    private static class InserPredictionAsyncTask extends AsyncTask<Predictions, Void, Long> {
        private PredictionsDao predictionsDao;

        private InserPredictionAsyncTask(PredictionsDao predictionsDao) {
            this.predictionsDao = predictionsDao;
        }
        @Override
        protected Long doInBackground(Predictions... predictions) {
            long key = predictionsDao.insertAlbum(predictions[0]);
            return key;
        }
    }

    private static class DetelePredictionAsyncTask extends AsyncTask<Predictions, Void, Void> {
        private PredictionsDao predictionsDao;

        private DetelePredictionAsyncTask(PredictionsDao predictionsDao) {
            this.predictionsDao = predictionsDao;
        }

        @Override
        protected Void doInBackground(Predictions... predictions) {
            predictionsDao.delete(predictions[0]);
            return null;
        }
    }


    private static class DeleteSpecificPreicationAsyncTask extends AsyncTask<Predictions, Void, Void> {
        private PredictionsDao predictionsDao;

        private DeleteSpecificPreicationAsyncTask(PredictionsDao predictionsDao) {
            this.predictionsDao = predictionsDao;
        }

        @Override
        protected Void doInBackground(Predictions... predictions) {
            Predictions predictions1 =  predictions[0];
            predictionsDao.deleteSpecifPredictions(predictions1.getId());
            return null;
        }
    }

    public long insertPendingPredictions(PendingPredictions pendingPredictions) {
        try {
            return new InserPendingPredictionAsyncTask(predictionsDao).execute(pendingPredictions).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Expection call " + e.getMessage());
            return -1;
        }
    }


    private static class InserPendingPredictionAsyncTask extends AsyncTask<PendingPredictions, Void, Long> {
        private PredictionsDao predictionsDao;

        private InserPendingPredictionAsyncTask(PredictionsDao predictionsDao) {
            this.predictionsDao = predictionsDao;
        }
        @Override
        protected Long doInBackground(PendingPredictions... pendingPredictions) {
            long key = predictionsDao.insertPendingPredictions(pendingPredictions[0]);
            return key;
        }
    }

    public List<PendingPredictions> getPendingPredictionsBaseONDate(String date) {
     return predictionsDao.getPendingPredictionsBaseONDate(date);
    }




}
