package com.bettingtipsking.app.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AuthSignupRepository {

    private Application application;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private String TAG = "AuthSignupRepository:";

    public AuthSignupRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
    }

    public void Signup(String name, String email, String password) {
        if (email.isEmpty() || TextUtils.isEmpty(email)){
            QuickHelp.showSimpleToast(application, "Name is required");
        }else {
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
                                signupWithEmailPassword(name,email, password);
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

    private void signupWithEmailPassword(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    saveUserInfoToFireStore(Config.ACCOUNT_TYPE_EMAIL_PASSWORD, user.getUid(), name, email, password);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                }
            }
        });
    }

/*
    public void checkUserDatabase(String accountType, String uid, String name, String email, String password) {
        firebaseFirestore.collection(Config.USER).document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(command -> {
            if (command.exists()) {
                Log.i(TAG, "user already exits");
            } else {
                Log.i(TAG, "user not exits");
                saveUserInfoToFireStore( accountType,  uid,  name,  email,  password);
            }
        });

    }
*/

    public void saveUserInfoToFireStore(String accountType, String uid, String name, String email, String password) {
        CollectionReference dbUser = firebaseFirestore.collection(Config.USER);
        User user = new User( uid, name, email, "", "", password, "", "");
        dbUser.add(user).addOnCompleteListener(command -> {
            if (command.isSuccessful()) {
                userMutableLiveData.postValue(mAuth.getCurrentUser());
            } else {
                userMutableLiveData.postValue(null);
            }
        });
    }
}
