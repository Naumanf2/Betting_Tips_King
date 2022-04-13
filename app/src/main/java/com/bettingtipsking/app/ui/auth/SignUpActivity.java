package com.bettingtipsking.app.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivitySignUpBinding;
import com.bettingtipsking.app.Helper.SharedSharedPreferencesUtils;
import com.bettingtipsking.app.ui.home.HomeActivity;
import com.bettingtipsking.app.viewmodel.AuthSignupViewModel;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    AuthSignupViewModel authSignupViewModel;
    SharedSharedPreferencesUtils utils = new SharedSharedPreferencesUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        authSignupViewModel = new ViewModelProvider(this).get(AuthSignupViewModel.class);

        authSignupViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(this, "Successfully Signup", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            } else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

        });

        authSignupViewModel.getProgressMutableLiveData().observe(this,integer -> {
            if (integer==0)
                binding.progressBar.setVisibility(View.VISIBLE);
            else
                binding.progressBar.setVisibility(View.GONE);
        });

        binding.setAuthViewModel(authSignupViewModel);


    }
}