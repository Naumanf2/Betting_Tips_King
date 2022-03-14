package com.bettingtipsking.app.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

  ActivityLogInBinding binding;
  String changedbyNOmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = DataBindingUtil.setContentView(this,R.layout.activity_log_in);
        setContentView(R.layout.activity_log_in);



    }
}