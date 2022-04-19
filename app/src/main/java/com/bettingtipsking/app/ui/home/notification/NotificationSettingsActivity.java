package com.bettingtipsking.app.ui.home.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.Helper.SharedSharedPreferencesUtils;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityNotificationSettingBinding;
import com.bettingtipsking.app.databinding.ActivityNotificationSettingsBinding;

public class NotificationSettingsActivity extends AppCompatActivity {

    ActivityNotificationSettingsBinding binding;
    SharedSharedPreferencesUtils sharedSharedPreferencesUtils;
    boolean allNotification, matchesNotification, videoNotification, newsNotification, importantNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_settings);

        sharedSharedPreferencesUtils = new SharedSharedPreferencesUtils();

        allNotification = sharedSharedPreferencesUtils.getBoolean(Config.CLOSE_ALL_NOTIFICATION, true);
        matchesNotification = sharedSharedPreferencesUtils.getBoolean(Config.MATCHES_NOTIFICATION, true);
        videoNotification = sharedSharedPreferencesUtils.getBoolean(Config.VIDEOS_NOTIFICATION, true);
        newsNotification = sharedSharedPreferencesUtils.getBoolean(Config.NEWS_NOTIFICATION, true);
        importantNotification = sharedSharedPreferencesUtils.getBoolean(Config.IMPORTANT_NOTIFICATION, true);
        checkSwitchesStates();


        binding.closeNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedSharedPreferencesUtils.setBoolean(Config.CLOSE_ALL_NOTIFICATION, true);
                    binding.matchesNotificationSwitch.setChecked(true);
                    binding.videosNotificationSwitch.setChecked(true);
                    binding.newsNotificationSwitch.setChecked(true);
                } else {
                    sharedSharedPreferencesUtils.setBoolean(Config.CLOSE_ALL_NOTIFICATION, false);
                    binding.matchesNotificationSwitch.setChecked(false);
                    binding.videosNotificationSwitch.setChecked(false);
                    binding.newsNotificationSwitch.setChecked(false);
                }
            }
        });

        binding.matchesNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedSharedPreferencesUtils.setBoolean(Config.MATCHES_NOTIFICATION, true);
                } else {
                    sharedSharedPreferencesUtils.setBoolean(Config.MATCHES_NOTIFICATION, false);
                    System.out.println("ValueIS" + sharedSharedPreferencesUtils.getBoolean(Config.MATCHES_NOTIFICATION, true));
                    Toast.makeText(NotificationSettingsActivity.this, "" + sharedSharedPreferencesUtils.getBoolean(Config.MATCHES_NOTIFICATION, true), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.videosNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedSharedPreferencesUtils.setBoolean(Config.VIDEOS_NOTIFICATION, true);

                } else {
                    sharedSharedPreferencesUtils.setBoolean(Config.VIDEOS_NOTIFICATION, false);
                }
            }
        });

        binding.newsNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedSharedPreferencesUtils.setBoolean(Config.NEWS_NOTIFICATION, true);

                } else {
                    sharedSharedPreferencesUtils.setBoolean(Config.NEWS_NOTIFICATION, false);
                }
            }
        });


    }

    public void checkSwitchesStates() {
        if (!allNotification) {
            binding.closeNotificationSwitch.setChecked(false);
            binding.matchesNotificationSwitch.setChecked(false);
            binding.videosNotificationSwitch.setChecked(false);
            binding.newsNotificationSwitch.setChecked(false);
        } else {
            binding.closeNotificationSwitch.setChecked(true);
            binding.matchesNotificationSwitch.setChecked(true);
            binding.videosNotificationSwitch.setChecked(true);
            binding.newsNotificationSwitch.setChecked(true);

        }

        if (matchesNotification) {
            binding.matchesNotificationSwitch.setChecked(true);
        } else {
            binding.matchesNotificationSwitch.setChecked(false);
        }


        if (videoNotification) {
            binding.videosNotificationSwitch.setChecked(true);
        } else {
            binding.videosNotificationSwitch.setChecked(false);
        }

        if (newsNotification) {
            binding.newsNotificationSwitch.setChecked(true);
        } else {
            binding.newsNotificationSwitch.setChecked(false);
        }

    }
}