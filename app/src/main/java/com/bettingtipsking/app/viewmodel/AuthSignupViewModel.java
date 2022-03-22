package com.bettingtipsking.app.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.repository.AuthSignupRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthSignupViewModel extends AndroidViewModel {

    Application application;
    AuthSignupRepository authRepository;
    MutableLiveData<FirebaseUser> userMutableLiveData;

    public AuthSignupViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        authRepository = new AuthSignupRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();

    }

    public void signup(String name, String email, String password){
        authRepository.Signup(name, email,password);
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
