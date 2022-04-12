package com.bettingtipsking.app.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordRepository {
    private Application application;
    private FirebaseAuth mAuth;
    private MutableLiveData<Integer> mutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;
    String TAG = "ResetPasswordRepository:";

    public ResetPasswordRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        mutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getMutableLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public void resetPassword(String email) {
        if (TextUtils.isEmpty(email) || email.isEmpty()) {
            QuickHelp.showSimpleToast(application, "Email is empty");
        } else {
            if (QuickHelp.validateEmail(email)) {
                if (QuickHelp.isInternetAvailable(application)) {
                    repositoryResetPassword(email);
                } else {
                    Toast.makeText(application, "internet not available", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(application, "email is not valid", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void repositoryResetPassword(String email) {
        progressMutableLiveData.postValue(0);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressMutableLiveData.postValue(1);
                    mutableLiveData.postValue(0);
                    Log.d(TAG, "Email sent.");
                } else {
                    mutableLiveData.postValue(1);
                    Log.d(TAG, "Email not sent.");
                }
            }
        });
    }

}
