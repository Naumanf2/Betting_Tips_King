package com.bettingtipsking.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.model.User;
import com.bettingtipsking.app.repository.FetchUserDetailRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FetchUserDetailViewModel extends AndroidViewModel {

    Application application;
    private FirebaseAuth mAuth;
    private FetchUserDetailRepository fetchUserDetailRepository;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Integer> disable;

    public FetchUserDetailViewModel(Application application) {
        super(application);
        this.application = application;
        fetchUserDetailRepository = new FetchUserDetailRepository(application);
        userMutableLiveData = fetchUserDetailRepository.getUserMutableLiveData();
        disable = fetchUserDetailRepository.getDisable();
    }

    public MutableLiveData<User> getUserData() {
        return userMutableLiveData;
    }
    public MutableLiveData<Integer> getDisable() {
        return disable;
    }

    public void fetchUser() {
      fetchUserDetailRepository.fetchUserData();
    }

    public void updateUser(String name, String email) {
        fetchUserDetailRepository.updateUserData(name, email);
    }
}
