package com.bettingtipsking.app.ui.home.matches.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.style.AlignmentSpan;
import android.view.View;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.PredictionFragment;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityMatchDetailsBinding;
import com.bettingtipsking.app.model.fixture_by_fixture_id.FixtureByFixtureId;
import com.bettingtipsking.app.model.fixture_by_fixture_id.Response;
import com.bettingtipsking.app.model.leaguebyleague.Coverage;
import com.bettingtipsking.app.model.leaguebyleague.Season;
import com.bettingtipsking.app.ui.home.matches.details.fragment.CoachsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.EventsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.FixturePredictionsFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.H2HFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.LineupPlayersFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.SquadFragment;
import com.bettingtipsking.app.ui.home.matches.details.fragment.StatisticsFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.ComingFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.LiveFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.PastFixturesFragment;
import com.bettingtipsking.app.ui.home.matches.fragment.TodayFixturesFragment;
import com.bettingtipsking.app.viewmodel.H2HViewModel;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.MatchDetailsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.H2HModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.MatchDetailsViewViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailsActivity extends AppCompatActivity {

    ActivityMatchDetailsBinding binding;
    MatchDetailsViewModel viewModel;
    LeagueViewModel leagueViewModel;
    FixtureByFixtureId fixture;
    List<Fragment> list;

    int fixture_id;
    int league_id;
    int team_home_id;
    int team_away_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_details);
        viewModel = new ViewModelProvider(this, new MatchDetailsViewViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(MatchDetailsViewModel.class);

        list = new ArrayList<>();
        fixture_id = getIntent().getIntExtra("fixture_id", 00);
        league_id = getIntent().getIntExtra("league_id", 00);
        team_home_id = getIntent().getIntExtra("team_home_id", 00);
        team_away_id = getIntent().getIntExtra("team_away_id", 00);

        System.out.println("fixture id is "+fixture_id);
        System.out.println("league id is "+league_id);

        viewModel.getFixturesBYFixtureId(fixture_id);
        viewModel.getLeagueById(league_id);
        viewModel.getMatchDetailsLiveData().observe(this, fixturesModel -> {

            binding.setMatchDetail(fixturesModel);

            List<Response> responses = fixturesModel.getResponse();

            if (!responses.isEmpty() && responses.size() > 0) {
                if (!String.valueOf(responses.get(0).getGoals().getHome()).equals("null")) {
                    binding.textMatchScore.setText(responses.get(0).getGoals().getHome() + " - " + responses.get(0).getGoals().getAway());
                } else {
                    binding.textMatchScore.setText("--");
                }

                String shortStatus = responses.get(0).getFixture().getStatus().getShort();


                if (shortStatus.equalsIgnoreCase("TBD")) {

                } else if (shortStatus.equalsIgnoreCase("")) {
                    shortStatusLogic("Time To Be Defined", 0);
                } else if (shortStatus.equalsIgnoreCase("NS")) {
                    shortStatusLogic("Not Started", 0);

                } else if (shortStatus.equalsIgnoreCase("1H")) {
                    shortStatusLogic("First Half, Kick Off", 1);
                } else if (shortStatus.equalsIgnoreCase("HT")) {
                    shortStatusLogic("Halftime", 1);
                } else if (shortStatus.equalsIgnoreCase("2H")) {
                    shortStatusLogic("2nd Half Started", 1);
                } else if (shortStatus.equalsIgnoreCase("ET")) {
                    shortStatusLogic("Extra Time", 0);
                } else if (shortStatus.equalsIgnoreCase("P")) {
                    shortStatusLogic("Penalty In Progress", 0);
                } else if (shortStatus.equalsIgnoreCase("FT")) {
                    shortStatusLogic("Match Finished", 0);
                } else if (shortStatus.equalsIgnoreCase("AET")) {
                    shortStatusLogic("Match Finished After Extra Time", 0);
                } else if (shortStatus.equalsIgnoreCase("PEN")) {
                    shortStatusLogic("Match Finished After Penalty", 0);
                } else if (shortStatus.equalsIgnoreCase("BT")) {
                    shortStatusLogic("Break Time (in Extra Time", 0);
                } else if (shortStatus.equalsIgnoreCase("SUSP")) {
                    shortStatusLogic("Match Suspended", 0);
                } else if (shortStatus.equalsIgnoreCase("INT")) {
                    shortStatusLogic("Match Interrupted", 0);
                } else if (shortStatus.equalsIgnoreCase("PST")) {
                    shortStatusLogic("Match Postponed", 0);
                } else if (shortStatus.equalsIgnoreCase("CANC")) {
                    shortStatusLogic("Match Cancelled", 0);
                } else if (shortStatus.equalsIgnoreCase("ABD")) {
                    shortStatusLogic("Match Abandoned", 0);
                } else if (shortStatus.equalsIgnoreCase("WO")) {
                    shortStatusLogic("WalkOver", 0);
                } else if (shortStatus.equalsIgnoreCase("LIVE")) {
                    shortStatusLogic("Live", 1);
                }

                binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Predication"));
                list.add(new FixturePredictionsFragment(fixture_id, league_id, team_home_id, team_away_id));

                binding.tabLayout.addTab(binding.tabLayout.newTab().setText("H2H"));
                list.add(new H2HFragment(fixture_id, league_id, team_home_id, team_away_id));

                if (!responses.get(0).getLineups().isEmpty()){
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Line up"));
                    list.add(new LineupPlayersFragment(fixture_id, league_id, team_home_id, team_away_id));
                }

                if (!responses.get(0).getEvents().isEmpty() &&responses.get(0).getEvents().size()>0){
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Events"));
                    list.add(new EventsFragment(fixture_id, league_id, team_home_id, team_away_id));
                }

                if (!responses.get(0).getPlayers().isEmpty() && responses.get(0).getPlayers().size()>0){
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Squad"));
                    list.add(new SquadFragment(fixture_id, league_id, team_home_id, team_away_id));
                }

                binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Statistics"));
                list.add(new StatisticsFragment(fixture_id, league_id, team_home_id, team_away_id));

                binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Coaches"));
                list.add(new CoachsFragment(fixture_id, league_id, team_home_id, team_away_id));

                FragmentManager fm = getSupportFragmentManager();
                ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle(), list);
                binding.viewpager2.setAdapter(sa);
            }

        });

        /*viewModel.getLeagueByIdLiveData().observe(this, leagueModel -> {

            System.out.println("league model is "+leagueModel);

            List<com.bettingtipsking.app.model.leaguebyleague.Response> responses = leagueModel.getResponse();

            if (!responses.isEmpty() && responses.size() > 0) {
                List<Season> seasons = responses.get(0).getSeasons();

                Coverage coverage = seasons.get(seasons.size() - 1).getCoverage();





                if (coverage.getFixtures().getLineups()){

                }

                if (coverage.getFixtures().getEvents()){

                }

                if (coverage.getPlayers()){

                }
                if (coverage.getFixtures().getStatistics_fixtures()){
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Statistics"));
                    list.add(new StatisticsFragment(fixture_id, league_id, team_home_id, team_away_id));
                }




            }
        });
*/
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

    }

    private void shortStatusLogic(String message, int logic) {
        binding.textStatus.setText(message);
        if (logic == 1)
            binding.spinKitWave.setVisibility(View.VISIBLE);
        else
            binding.spinKitWave.setVisibility(View.GONE);
    }

}


class ViewStateAdapter extends FragmentStateAdapter {
    List<Fragment> fragments;

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (fragments.size() == 3) {
            if (position == 0) return fragments.get(0);
            if (position == 1) return fragments.get(1);
            if (position == 2) return fragments.get(2);
        } else if (fragments.size() == 4) {
            if (position == 0) return fragments.get(0);
            if (position == 1) return fragments.get(1);
            if (position == 2) return fragments.get(2);
            if (position == 3) return fragments.get(3);
        } else if (fragments.size() == 5) {
            if (position == 0) return fragments.get(0);
            if (position == 1) return fragments.get(1);
            if (position == 2) return fragments.get(2);
            if (position == 3) return fragments.get(3);
            if (position == 4) return fragments.get(4);
        } else if (fragments.size() == 6) {
            if (position == 0) return fragments.get(0);
            if (position == 1) return fragments.get(1);
            if (position == 2) return fragments.get(2);
            if (position == 3) return fragments.get(3);
            if (position == 4) return fragments.get(4);
            if (position == 5) return fragments.get(5);
        } else if (fragments.size() == 7) {
            if (position == 0) return fragments.get(0);
            if (position == 1) return fragments.get(1);
            if (position == 2) return fragments.get(2);
            if (position == 3) return fragments.get(3);
            if (position == 4) return fragments.get(4);
            if (position == 5) return fragments.get(5);
            if (position == 6) return fragments.get(6);
        }

        throw new IllegalStateException("Position is unexpectedly " + position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}