package com.bettingtipsking.app.ui.home.matches;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bettingtipsking.app.R;

public class MatchDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InjuryFragment()).commit();
    }
}