package com.bettingtipsking.app.ui.home.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityAccountBinding;
import com.bettingtipsking.app.ui.contact.ContactActivity;
import com.bettingtipsking.app.ui.home.notification.NotificationSettingActivity;
import com.bettingtipsking.app.ui.profile.EditProfileActivity;

public class AccountActivity extends AppCompatActivity {

    ActivityAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);


        binding.textProfileSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
        });



        binding.textSupportContact.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactActivity.class));
        });

        binding.textNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationSettingActivity.class));
        });
    }
}