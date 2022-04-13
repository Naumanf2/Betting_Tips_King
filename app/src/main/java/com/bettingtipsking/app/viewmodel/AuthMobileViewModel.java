package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.repository.AuthLoginRepository;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class AuthMobileViewModel extends AndroidViewModel {
    Application application;
    AuthLoginRepository repository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public AuthMobileViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new AuthLoginRepository(application);
        userMutableLiveData = repository.getUserMutableLiveData();
        progressMutableLiveData = repository.getProgressMutableLiveData();
    }

    public void loginWithCredentials(AuthCredential credential, String mobileNumber) {
        repository.loginWithCredentials(credential, mobileNumber);
    }
}
