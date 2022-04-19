package com.bettingtipsking.app.ui.home.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityAccountBinding;
import com.bettingtipsking.app.ui.auth.LogInActivity;
import com.bettingtipsking.app.ui.contact.ContactActivity;
import com.bettingtipsking.app.ui.home.notification.NotificationSettingActivity;
import com.bettingtipsking.app.ui.home.notification.NotificationSettingsActivity;
import com.bettingtipsking.app.ui.profile.EditProfileActivity;
import com.bettingtipsking.app.viewmodel.FetchUserDetailViewModel;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    ActivityAccountBinding binding;
    FetchUserDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        viewModel = new ViewModelProvider(this).get(FetchUserDetailViewModel.class);


        viewModel.fetchUser();
        viewModel.getDisable().observe(this, integer -> {

            if (integer == 1) {
                binding.edittName.setEnabled(false);
                binding.editEmail.setEnabled(false);
                binding.imageSaveBtn.setImageResource(0);
            }

        });

        viewModel.getUserData().observe(this, user -> {
            binding.edittName.setText(user.getName());
            binding.textUID.setText(user.getUid());
            if (user.getEmail().isEmpty()) {
                binding.editEmail.setText(user.getMobile_number());

            } else
                binding.editEmail.setText(user.getEmail());
        });
        binding.imageCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", binding.textUID.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(AccountActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        binding.edittName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageSaveBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_done_24));
            }
        });
        binding.editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageSaveBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_done_24));
            }
        });

        binding.imageSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateUser(binding.edittName.getText().toString(), binding.editEmail.getText().toString());
            }
        });

        binding.textProfileSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
        });

        binding.textSupportContact.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactActivity.class));
        });

        binding.textNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationSettingsActivity.class));
        });

        binding.textLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AccountActivity.this, LogInActivity.class));
            finish();
        });

        binding.textShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        binding.textFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.bettingtipsking.app"));
            startActivity(intent);
        });
    }


}