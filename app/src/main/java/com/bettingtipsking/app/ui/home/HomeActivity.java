package com.bettingtipsking.app.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;

import com.bettingtipsking.app.databinding.ActivityHomeBinding;
import com.bettingtipsking.app.databinding.HomePredicationDialogeBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Home;
import com.bettingtipsking.app.ui.auth.LogInActivity;
import com.bettingtipsking.app.ui.home.account.AccountActivity;
import com.bettingtipsking.app.ui.home.account.AccountLoginFragment;
import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.ui.home.matches.FixturesFragment;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.bettingtipsking.app.ui.home.matches.details.fragment.CoachsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.EventsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.FixturePredictionsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.H2HFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.SquadFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.StatisticsFragment;
import com.bettingtipsking.app.ui.home.news.NewsDetailsActivity;
import com.bettingtipsking.app.ui.home.news.NewsFragment;
import com.bettingtipsking.app.ui.home.notification.NotificationsFragment;
import com.bettingtipsking.app.ui.home.video.VideosFragment;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.EventsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int screen_id = Integer.parseInt(extras.getString("screen_id"));
            if (screen_id == 0) {
                int id = Integer.parseInt(extras.getString("id"));
                String status = extras.getString("status");
                String league_name = extras.getString("league_name");
                String match_id = extras.getString("match_id");
                String home_team = extras.getString("home_team");
                String away_team = extras.getString("away_team");
                String odd_value = extras.getString("odd_value");
                String home_score = extras.getString("home_score");
                String away_score = extras.getString("away_score");
                String match_minute = extras.getString("match_minute");
                String game_prediction = extras.getString("game_prediction");
                String match_date = extras.getString("match_date");
                String match_status = extras.getString("match_status");
                String match_time = extras.getString("match_time");

                showDialoge(id, status, league_name, match_id,
                        home_team, away_team, odd_value, home_score, away_score, match_minute, game_prediction,
                        match_date, match_status, match_time);
            } else if (screen_id == 1) {
                int fixture_id = Integer.parseInt(extras.getString("fixture_id"));
                int league_id = Integer.parseInt(extras.getString("league_id"));
                int team_home_id = Integer.parseInt(extras.getString("team_home_id"));
                int team_away_id = Integer.parseInt(extras.getString("team_away_id"));

                Intent intent = new Intent(this, MatchDetailsActivity.class);
                intent.putExtra("fixture_id", fixture_id);
                intent.putExtra("league_id", league_id);
                intent.putExtra("team_home_id", team_home_id);
                intent.putExtra("team_away_id", team_away_id);
                startActivity(intent);

            } else if (screen_id == 2) {
                String introduction = extras.getString("introduction");
                String localteam = extras.getString("localteam");
                String visitorteam = extras.getString("visitorteam");
                String updated_at = extras.getString("updated_at");

                Intent intent = new Intent(this, NewsDetailsActivity.class);
                intent.putExtra("introduction", introduction);
                intent.putExtra("date", updated_at);
                intent.putExtra("localTeam", localteam);
                intent.putExtra("visitorTeam", visitorteam);
                startActivity(intent);

            } else if (screen_id == 3) {

            }

        }

        FirebaseMessaging.getInstance().subscribeToTopic("bettingTipsKing");

        loadFragment(new HomeFragment());
        binding.bottomNavigationView.setItemIconTintList(null);
        binding.bottomNavigationView.setOnItemSelectedListener(this);

        binding.circleImageProfile.setOnClickListener(v -> {
            QuickHelp.goToActivityWithNoClean(this, AccountActivity.class);
        });

    }

    private void showDialoge(int id, String status, String league_name, String match_id, String home_team, String away_team, String odd_value, String home_score, String away_score, String match_minute, String game_prediction, String match_date, String match_status, String match_time) {

        final Dialog dialog = new Dialog(this);

        HomePredicationDialogeBinding dialogeBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.home_predication_dialoge, null, false);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(dialogeBinding.getRoot());

        dialogeBinding.txtLeague.setText(league_name);
        dialogeBinding.txtDate.setText(match_date);
        dialogeBinding.txtTime.setText(match_time);
        dialogeBinding.txtHomeTeam.setText(home_team);
        dialogeBinding.txtAwayScore.setText(away_team);
        dialogeBinding.txtHomeScore.setText(home_score);
        dialogeBinding.txtAwayScore.setText(away_score);
        dialogeBinding.txtOddValue.setText(odd_value);
        dialogeBinding.textView5.setText(match_status);
        dialogeBinding.txtPredications.setText(game_prediction);

        dialogeBinding.imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

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

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }
    }

    {
    }
}