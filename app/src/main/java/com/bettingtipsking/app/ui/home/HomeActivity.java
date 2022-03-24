package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.api.MatchesRetrofitHelper;
import com.bettingtipsking.app.api.MatchesService;
import com.bettingtipsking.app.api.NewsService;
import com.bettingtipsking.app.api.RetrofitHelper;
import com.bettingtipsking.app.model.coach.CoachesModel;
import com.bettingtipsking.app.model.predictions.PredictionsModel;
import com.bettingtipsking.app.repository.CoachesRepository;
import com.bettingtipsking.app.repository.FixturesByDateRepository;
import com.bettingtipsking.app.repository.NewsRepository;
import com.bettingtipsking.app.repository.PredictionsRepository;
import com.bettingtipsking.app.ui.home.matches.CoachFragment;
import com.bettingtipsking.app.ui.home.account.AccountLoginFragment;
import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.ui.home.news.NewsFragment;
import com.bettingtipsking.app.ui.home.notification.NotificationsFragment;

import com.bettingtipsking.app.ui.home.matches.MatchesFragment;
import com.bettingtipsking.app.ui.home.video.VideosFragment;
import com.bettingtipsking.app.viewmodel.CoachesViewModel;
import com.bettingtipsking.app.viewmodel.FixturesByDateViewModel;
import com.bettingtipsking.app.viewmodel.NewsViewModel;
import com.bettingtipsking.app.viewmodel.PredictionsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.CoachesViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.FixturesByDateViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.NewsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.PredictionsViewModelFactory;

import kotlinx.coroutines.Dispatchers;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    PredictionsViewModel predictionsViewModel;

    CoachesViewModel coachesViewModel;

    FixturesByDateViewModel fixturesByDateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        PredictionsRepository predictionsRepository = new PredictionsRepository(MatchesRetrofitHelper.INSTANCE.getInstance().create(MatchesService.class));
        predictionsViewModel = new ViewModelProvider(this, new PredictionsViewModelFactory(predictionsRepository)).get(PredictionsViewModel.class);
        predictionsViewModel.getPredictions(813194);


        CoachesRepository coachesRepository = new CoachesRepository(MatchesRetrofitHelper.INSTANCE.getInstance().create(MatchesService.class));
        coachesViewModel = new ViewModelProvider(this, new CoachesViewModelFactory(coachesRepository)).get(CoachesViewModel.class);
        coachesViewModel.getCoach(3611);
        coachesViewModel.getCoach().observe(this, new Observer<CoachesModel>() {
            @Override
            public void onChanged(CoachesModel coachesModel) {

            }
        });


        FixturesByDateRepository fixturesByDateRepository = new FixturesByDateRepository(MatchesRetrofitHelper.INSTANCE.getInstance().create(MatchesService.class));
        fixturesByDateViewModel = new ViewModelProvider(this, new FixturesByDateViewModelFactory(fixturesByDateRepository)).get(FixturesByDateViewModel.class);
        fixturesByDateViewModel.getDate("2022-03-22");


    }
}