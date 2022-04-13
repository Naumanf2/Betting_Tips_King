package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bettingtipsking.app.model.news.NewsModel;
import com.bettingtipsking.app.repository.ResetPasswordRepository;

import java.util.List;

public class ResetPasswordViewModel extends AndroidViewModel {
    private ResetPasswordRepository repository;
    private MutableLiveData<Integer> mutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;


    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        repository = new ResetPasswordRepository(application);
        mutableLiveData = repository.getMutableLiveData();
        progressMutableLiveData = repository.getProgressMutableLiveData();
    }

    public MutableLiveData<Integer> getMutableLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public void resetPassword(String email) {
        repository.resetPassword(email);
    }
}
