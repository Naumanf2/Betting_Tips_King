package com.bettingtipsking.app.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.repository.AuthLoginRepository;
import com.bettingtipsking.app.repository.AuthSignupRepository;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;


public class AuthLoginViewModel extends AndroidViewModel {
    Application application;
    AuthLoginRepository repository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> errorMutableLiveData;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getErrorMutableLiveData() {
        return errorMutableLiveData;
    }

    public AuthLoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new AuthLoginRepository(application);
        userMutableLiveData = repository.getUserMutableLiveData();
        errorMutableLiveData = repository.getErrorMutableLiveData();
    }

    public void loginWithEmailPassword(String email, String password) {
        repository.loginWithEmailPassword(email, password);
    }

    public void loginWithCredentials(AuthCredential credential) {
        repository.loginWithCredentials(credential);
    }


}
