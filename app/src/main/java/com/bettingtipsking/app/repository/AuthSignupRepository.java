package com.bettingtipsking.app.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.Helper.SharedSharedPreferencesUtils;
import com.bettingtipsking.app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthSignupRepository {

    private Application application;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;
    private String TAG = "AuthSignupRepository:";
    private SharedSharedPreferencesUtils preferencesUtils;

    public AuthSignupRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        preferencesUtils = new SharedSharedPreferencesUtils();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();

    }

    public void Signup(String name, String email, String password) {
        if (email.isEmpty() || TextUtils.isEmpty(email)) {
            QuickHelp.showSimpleToast(application, "Name is required");
        } else {
            if (email.isEmpty() || TextUtils.isEmpty(email)) {
                QuickHelp.showSimpleToast(application, "Email is required");
            } else {
                if (password.isEmpty() || TextUtils.isEmpty(password)) {
                    QuickHelp.showSimpleToast(application, "Password is required");
                } else {
                    if (password.length() < 6) {
                        Toast.makeText(application, "Password must be greater then 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        if (QuickHelp.validateEmail(email)) {
                            if (QuickHelp.isInternetAvailable(application)) {
                                signupWithEmailPassword(name, email, password);
                            } else {
                                QuickHelp.showSimpleToast(application, "Internet not available");
                            }
                        } else {
                            QuickHelp.showSimpleToast(application, "Email is not valid");
                        }
                    }
                }
            }
        }

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    private void signupWithEmailPassword(String name, String email, String password) {
        progressMutableLiveData.postValue(0);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    saveUserInfoToFireStore(user.getUid(), name, email);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    progressMutableLiveData.postValue(1);
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                }
            }
        });
    }

    public void saveUserInfoToFireStore(String uid, String name, String email) {
        CollectionReference dbUser = firebaseFirestore.collection(Config.USER);
        User user = new User(uid, name, email, "", "", "", "");
        dbUser.document(uid).set(user).addOnCompleteListener(command -> {
            if (command.isSuccessful()) {
                progressMutableLiveData.postValue(1);
                preferencesUtils.setBoolean(Config.USER_SHARED_SUBSCRIPTION_STATUS,false);
                preferencesUtils.setBoolean(Config.SHARED_USER_DATA_SAVE_STATUS,true);
                userMutableLiveData.postValue(mAuth.getCurrentUser());
            } else {
                progressMutableLiveData.postValue(1);
                userMutableLiveData.postValue(null);
            }
        });
    }
}
