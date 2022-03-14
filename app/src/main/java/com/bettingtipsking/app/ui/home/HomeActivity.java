package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.ui.home.matches.MatchesFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout, new MatchesFragment()).commit();
    }
}