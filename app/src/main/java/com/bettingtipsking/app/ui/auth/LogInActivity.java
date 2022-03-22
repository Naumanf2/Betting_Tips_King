package com.bettingtipsking.app.ui.auth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityLogInBinding;
import com.bettingtipsking.app.ui.home.HomeActivity;
import com.bettingtipsking.app.viewmodel.AuthLoginViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LogInActivity extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private AuthLoginViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private ActivityResultLauncher<Intent> googleIntentActivityResultLauncher;
    private String TAG = "LoginActivity:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in);
        viewModel = new ViewModelProvider(this).get(AuthLoginViewModel.class);
        binding.setLoginAuthViewModel(viewModel);

        createGoogleRequest();
        createFacebookRequest();
        googleActivityResultLauncher();

        binding.buttonCreateAccount.setOnClickListener(v -> {QuickHelp.goToActivityWithNoClean(LogInActivity.this, SignUpActivity.class); });
        binding.textForgetPassword.setOnClickListener(v -> {QuickHelp.goToActivityWithNoClean(this,ResetPasswordActivity.class); });
        binding.googleLogin.setOnClickListener(v -> {googleIntentActivityResultLauncher.launch(mGoogleSignInClient.getSignInIntent()); });
        binding.facebookLogin.setOnClickListener(v -> {facebookRequest(); });
        binding.phoneLogin.setOnClickListener(v -> {QuickHelp.goToActivityWithNoClean(LogInActivity.this, PhoneLoginActivity.class);});

        viewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                QuickHelp.showSimpleToast(getApplication(), "Something is wrong");
            }
        });
    }

    private void createGoogleRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void createFacebookRequest() {callbackManager = CallbackManager.Factory.create();}

    private void googleActivityResultLauncher() {
        googleIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    GoogleSignInAccount account = null;
                    try {
                        account = task.getResult(ApiException.class);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                    viewModel.loginWithCredentials(GoogleAuthProvider.getCredential(account.getIdToken(), null));
                } else {
                    QuickHelp.showSimpleToast(getApplication(),"Something is wrong");
                }
            }
        });
    }

    private void facebookRequest() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, " success facebook login");
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                viewModel.loginWithCredentials(credential);
            }
            @Override
            public void onCancel() {
                QuickHelp.showSimpleToast(getApplication(),"Facebook Login Cancel");
                Log.d(TAG, " cancel facebook login");
            }
            @Override
            public void onError(FacebookException error) {
                QuickHelp.showSimpleToast(getApplication(),"error facebook login" + error.toString());
                Log.d(TAG, "error facebook login" + error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}