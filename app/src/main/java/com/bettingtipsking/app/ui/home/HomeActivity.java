package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.activity.fixtures.PredictionFragment;
import com.bettingtipsking.app.ui.home.matches.FixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.EventsFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.FixturePredictionsFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.H2HFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.ComingFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.LiveFixturesFragment;


public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, new EventsFragment())
                .disallowAddToBackStack()
                .commit();
    }

}