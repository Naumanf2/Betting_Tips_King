package com.bettingtipsking.app.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.Helper.HelperClass;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.model.User;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthLoginRepository {
    private Application application;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Integer> errorMutableLiveData;

    private String TAG = "AuthLoginRepository:";

    public AuthLoginRepository(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        errorMutableLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getErrorMutableLiveData() {
        return errorMutableLiveData;
    }

    public void loginWithEmailPassword(String email, String password) {

        if (email.isEmpty() || TextUtils.isEmpty(email)) {
            QuickHelp.showSimpleToast(application, "Email is required");
            errorMutableLiveData.postValue(0);
        } else {
            if (password.isEmpty() || TextUtils.isEmpty(password)) {
                QuickHelp.showSimpleToast(application, "Password is required");
                errorMutableLiveData.postValue(0);
            } else {
                if (QuickHelp.validateEmail(email)) {
                    if (QuickHelp.isInternetAvailable(application)) {
                        signInWithEmail(email, password);
                    } else {
                        QuickHelp.showSimpleToast(application, "Internet not available");
                        errorMutableLiveData.postValue(99);
                    }

                } else {
                    QuickHelp.showSimpleToast(application, "Email is not valid");
                    errorMutableLiveData.postValue(1);
                }
            }


        }


    }

    public void loginWithCredentials(AuthCredential credential) {
        if (QuickHelp.isInternetAvailable(application)) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(command -> {
                if (command.isSuccessful())
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                else
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
            });
        } else {
            errorMutableLiveData.postValue(99);
        }

    }

    private void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    userMutableLiveData.postValue(mAuth.getCurrentUser());
                }
            }
        });

    }


    public void checkUserDatabase(String accountType, String uid, String name, String email, String mobile_number, String password) {
        firebaseFirestore.collection(Config.USER).document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(command -> {
            if (command.exists()) {
                Log.i(TAG, "user already exits");
            } else {
                Log.i(TAG, "user not exits");
                saveUserInfoToFireStore(accountType, uid, name, email, mobile_number, password);
            }
        });

    }


    public void saveUserInfoToFireStore(String accountType, String uid, String name, String email, String mobile_number, String password) {
        CollectionReference dbUser = firebaseFirestore.collection(Config.USER);
        User user = new User(uid, name, email, "", "", password, "", "");
        dbUser.add(user).addOnCompleteListener(command -> {
            if (command.isSuccessful()) {
                userMutableLiveData.postValue(mAuth.getCurrentUser());
            } else {
                userMutableLiveData.postValue(null);
            }
        });
    }

}
