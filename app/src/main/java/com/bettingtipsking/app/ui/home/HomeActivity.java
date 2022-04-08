package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.model.fixtures.Home;
import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.ui.home.matches.FixturesFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.CoachsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.EventsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.FixturePredictionsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.H2HFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.SquadFragment;
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
                .add(R.id.frameLayout, new FixturesFragment())
                .disallowAddToBackStack()
                .commit();
    }

}