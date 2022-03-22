package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.ui.home.account.AccountLoginFragment;
import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.ui.home.news.NewsFragment;
import com.bettingtipsking.app.ui.home.notification.NotificationsFragment;

import com.bettingtipsking.app.ui.home.matches.MatchesFragment;
import com.bettingtipsking.app.ui.home.video.VideosFragment;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();


    }
}