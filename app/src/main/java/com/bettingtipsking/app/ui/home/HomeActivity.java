package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.activity.fixtures.PredictionFragment;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.ui.home.matches.FixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.EventsFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.FixturePredictionsFragment;
import com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment.H2HFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.ComingFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.LiveFixturesFragment;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.EventsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;


public class HomeActivity extends AppCompatActivity {

LeagueViewModel viewModel;



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