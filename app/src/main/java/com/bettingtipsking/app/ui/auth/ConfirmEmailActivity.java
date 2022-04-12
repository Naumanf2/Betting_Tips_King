package com.bettingtipsking.app.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityConfirmEmailBinding;

public class ConfirmEmailActivity extends AppCompatActivity {
    ActivityConfirmEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_email);
        binding.imageBack.setOnClickListener(v -> {finish();});
        binding.textOpenEmail.setOnClickListener(v -> {openEmail();});
        binding.textSkip.setOnClickListener(v -> {finish();});
    }

    private void openEmail(){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            QuickHelp.showSimpleToast(getApplication(),"There is no email client installed.");
        }
    }
}