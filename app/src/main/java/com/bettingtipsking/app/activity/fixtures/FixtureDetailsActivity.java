package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.FIXTURE_DETAIL_MODEL;
import static com.bettingtipsking.app.Helper.HelperClass.FIXTURE_MODEL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.bettingtipsking.app.activity.fixtures.model.FinalFixturesModel;
import com.bettingtipsking.app.activity.fixtures.model.FinalMatchDetailsModel;
import com.bettingtipsking.app.Helper.AppBarStateChangeListener;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityFixtureDetailsBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class FixtureDetailsActivity extends AppCompatActivity {

    ActivityFixtureDetailsBinding binding;
    MatchDetailsPagerAdapter matchDetailsPagerAdapter;
    ViewPager viewPager;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsing_toolbar;
    Toolbar toolbar;
    LinearLayout rlToolbar;
    AnimationSet animation;
    boolean firstEntry;
    Animation animFade;
    public FinalFixturesModel fixturesModel = new FinalFixturesModel();
    public FinalMatchDetailsModel matchDetails = new FinalMatchDetailsModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fixture_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fixture_details);
        fixturesModel = (FinalFixturesModel)getIntent().getParcelableExtra(FIXTURE_MODEL);
        matchDetails = (FinalMatchDetailsModel)getIntent().getParcelableExtra(FIXTURE_DETAIL_MODEL);

        binding.setMatchDetails(matchDetails);
        binding.setFixture(fixturesModel);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        rlToolbar = findViewById(R.id.rlToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        matchDetailsPagerAdapter = new MatchDetailsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(matchDetailsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        firstEntry = true;

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int i) {
                if (!firstEntry) {
                    if (state.name().equals("EXPANDED")) {
                        animFade = AnimationUtils.loadAnimation(FixtureDetailsActivity.this, R.anim.fade_out);
                        rlToolbar.startAnimation(animFade);
                        animFade.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}
                            @Override
                            public void onAnimationEnd(Animation arg0) {
                                rlToolbar.setVisibility(View.INVISIBLE);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                    }else if (state.name().equals("COLLAPSED")) {
                        animFade = AnimationUtils.loadAnimation(FixtureDetailsActivity.this, R.anim.fade_in);
                        rlToolbar.startAnimation(animFade);
                        animFade.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}
                            @Override
                            public void onAnimationEnd(Animation arg0) {
                                rlToolbar.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                    }
                }
                firstEntry = false;
                Log.d("STATE", state.name());
            }
        });

        setupViews();

    }

    private void setupViews() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

class MatchDetailsPagerAdapter extends FragmentStatePagerAdapter {
    public MatchDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new PredictionFragment();
        }else if (position == 1) {
            fragment = new FixtureEventsFragment();
        }
        /*else if (position == 2) {
            fragment = new StatsFragment();
        }*/
        else if (position == 2) {
            fragment = new LineupPlayersFragment();
        }else if (position == 3) {
            fragment = new FixturesH2HFragment();
        }else {
            fragment = new StatsFragment();
        }
//        Bundle args = new Bundle();
//        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Prediction";
        }else if (position == 1) {
            return "Events";
        }
        /*else if (position == 2) {
            return "Stats";
        }*/
        else if (position == 2) {
            return "Lineups";
        }else if (position == 3) {
            return "Matches";
        }else {
            return "Stats";
        }
    }
}