package com.bettingtipsking.app.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.Helper.SharedSharedPreferencesUtils;
import com.bettingtipsking.app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class AuthLoginRepository {
    private Application application;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> progressMutableLiveData;
    private SharedSharedPreferencesUtils preferencesUtils;
    private String TAG = "AuthLoginRepository:";

    public AuthLoginRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        progressMutableLiveData = new MutableLiveData<>();
        preferencesUtils = new SharedSharedPreferencesUtils();

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public MutableLiveData<Integer> getProgressMutableLiveData() {
        return progressMutableLiveData;
    }

    public void loginWithEmailPassword(String email, String password) {

        if (email.isEmpty() || TextUtils.isEmpty(email)) {
            QuickHelp.showSimpleToast(application, "Email is required");
        } else {
            if (password.isEmpty() || TextUtils.isEmpty(password)) {
                QuickHelp.showSimpleToast(application, "Password is required");
            } else {
                if (QuickHelp.validateEmail(email)) {
                    if (QuickHelp.isInternetAvailable(application)) {
                        signInWithEmail(email, password);
                    } else {
                        QuickHelp.showSimpleToast(application, "Internet not available");
                    }

                } else {
                    QuickHelp.showSimpleToast(application, "Email is not valid");
                }
            }


        }


    }

    public void loginWithCredentials(AuthCredential credential, String mobileNumber) {
        progressMutableLiveData.postValue(0);
        if (QuickHelp.isInternetAvailable(application)) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(command -> {
                if (command.isSuccessful()) {
                    String email = command.getResult().getUser().getEmail();
                    String name = command.getResult().getUser().getDisplayName();
                    String phoneNumber = command.getResult().getUser().getPhoneNumber();

                    if (email == null || TextUtils.isEmpty(email)) {
                        email = "";
                    }
                    if (name==null || TextUtils.isEmpty(name)){
                        name = "";
                    }
                    if (phoneNumber==null || TextUtils.isEmpty(phoneNumber)){
                        phoneNumber = mobileNumber;
                    }

                    FirebaseUser user = mAuth.getCurrentUser();
                    checkUserDatabase(user.getUid(), email, name, phoneNumber);
                } else {
                    System.out.println("else call");
                    progressMutableLiveData.postValue(1);
                    userMutableLiveData.postValue(null);
                }
            });
        } else {
            QuickHelp.showSimpleToast(application, "Internet is not available");
        }

    }

    private void signInWithEmail(String email, String password) {
        progressMutableLiveData.postValue(0);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    checkUserDatabase(user.getUid(), email, "", "");
                } else {
                    // If sign in fails, display a message to the user.
                    progressMutableLiveData.postValue(1);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                }
            }
        });

    }


    public void checkUserDatabase(String uid, String email, String name, String mobileNumber) {
        firebaseFirestore.collection(Config.USER).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String uid = document.getString("uid");
                        String name = document.getString("name");
                        String email = document.getString("email");
                        String mobile_number = document.getString("mobile_number");
                        String image = document.getString("image");
                        String subscription_date = document.getString("subscription_date");
                        String subscription_package = document.getString("subscription_package");


                        System.out.println("User is already available");

                        //todo need to change the subscription value
                        preferencesUtils.setBoolean("abc", true);
                        preferencesUtils.setBoolean(Config.SHARED_USER_DATA_SAVE_STATUS, true);
                        progressMutableLiveData.postValue(1);
                        userMutableLiveData.postValue(mAuth.getCurrentUser());

                    } else {
                        Log.d(TAG, "No such document");
                        if (TextUtils.isEmpty(name)|| name.equals(""))
                            saveUserInfoToFireStore(uid, "User" + new Random().nextInt(1000000), email, mobileNumber);
                        else
                            saveUserInfoToFireStore(uid, name, email, mobileNumber);
                    }
                } else {
                    progressMutableLiveData.postValue(1);
                    userMutableLiveData.postValue(null);
                }
            }
        });
    }


    public void saveUserInfoToFireStore(String uid, String name, String email, String mobile_number) {
        CollectionReference dbUser = firebaseFirestore.collection(Config.USER);
        User user = new User(uid, name, email, mobile_number, "", "", "");
        dbUser.document(uid).set(user).addOnCompleteListener(command -> {
            if (command.isSuccessful()) {
                preferencesUtils.setBoolean(Config.USER_SHARED_SUBSCRIPTION_STATUS, false);
                preferencesUtils.setBoolean(Config.SHARED_USER_DATA_SAVE_STATUS, true);
                progressMutableLiveData.postValue(1);
                userMutableLiveData.postValue(mAuth.getCurrentUser());
            } else {
                progressMutableLiveData.postValue(1);
                userMutableLiveData.postValue(null);
            }
        });
    }

}
