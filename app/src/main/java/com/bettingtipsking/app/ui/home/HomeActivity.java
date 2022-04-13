package com.bettingtipsking.app.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;

import com.bettingtipsking.app.databinding.ActivityHomeBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Home;
import com.bettingtipsking.app.ui.home.account.AccountActivity;
import com.bettingtipsking.app.ui.home.account.AccountLoginFragment;
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
import com.bettingtipsking.app.ui.home.news.NewsFragment;
import com.bettingtipsking.app.ui.home.notification.NotificationsFragment;
import com.bettingtipsking.app.ui.home.video.VideosFragment;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.EventsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        loadFragment(new HomeFragment());
        binding.bottomNavigationView.setItemIconTintList(null);
        binding.bottomNavigationView.setOnItemSelectedListener(this);

        binding.circleImageProfile.setOnClickListener(v -> {
            QuickHelp.goToActivityWithNoClean(this, AccountActivity.class);
        });

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navHome:
                binding.txtHomeTitle.setText("Home Predication");
                fragment = new HomeFragment();
                break;
            case R.id.navMatch:
                binding.txtHomeTitle.setText("Fixtures");
                fragment = new FixturesFragment();
                break;
            case R.id.navNews:
                binding.txtHomeTitle.setText("News");
                fragment = new NewsFragment();
                break;
            case R.id.navVideo:
                binding.txtHomeTitle.setText("Videos");
                fragment = new VideosFragment();
                break;
            case R.id.navNotification:
                binding.txtHomeTitle.setText("Notifications");
                fragment = new NotificationsFragment();
                break;
        }

        return loadFragment(fragment);
    }
}