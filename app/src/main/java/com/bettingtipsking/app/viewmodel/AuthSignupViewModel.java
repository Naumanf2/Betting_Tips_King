package com.bettingtipsking.app.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.repository.AuthSignupRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthSignupViewModel extends AndroidViewModel {

    private Application application;
    private AuthSignupRepository authSignupRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;

    public AuthSignupViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        authSignupRepository = new AuthSignupRepository(application);
        userMutableLiveData = authSignupRepository.getUserMutableLiveData();
        progressMutableLiveData = authSignupRepository.getProgressMutableLiveData();

    }

    public void signup(String name, String email, String password) {
        authSignupRepository.Signup(name, email, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() { return progressMutableLiveData; }
}
