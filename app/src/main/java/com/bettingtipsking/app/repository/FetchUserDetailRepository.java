package com.bettingtipsking.app.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FetchUserDetailRepository {
    private FirebaseFirestore database;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Integer> disable;
    Application application;


    public FetchUserDetailRepository(Application application) {
        this.application = application;
        database = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        disable = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getDisable() {
        return disable;
    }


    public void fetchUserData() {
        database = FirebaseFirestore.getInstance();

        database.collection("USER").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    userMutableLiveData.postValue(new User(documentSnapshot.getString("name"), documentSnapshot.getString("email"), documentSnapshot.getString("mobile_number"),documentSnapshot.getId()));
                    disable.postValue(0);
                }

            }
        });

    }

    public void updateUserData(String name, String email) {

        System.out.println("Update is" + name);
        database.collection("USER").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("name", name, "email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(application, "Updated", Toast.LENGTH_SHORT).show();
                userMutableLiveData.postValue(new User(name, email));
                disable.postValue(1);
            }
        });

    }

}
