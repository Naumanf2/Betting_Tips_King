package com.bettingtipsking.app.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.bettingtipsking.app.R;

import com.bettingtipsking.app.api.MatchesRetrofitHelper;
import com.bettingtipsking.app.api.MatchesService;
import com.bettingtipsking.app.api.NewsService;
import com.bettingtipsking.app.api.RetrofitHelper;
import com.bettingtipsking.app.model.predictions.PredictionsModel;
import com.bettingtipsking.app.repository.NewsRepository;
import com.bettingtipsking.app.repository.PredictionsRepository;
import com.bettingtipsking.app.ui.home.matches.CoachFragment;
import com.bettingtipsking.app.ui.home.account.AccountLoginFragment;
import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.ui.home.news.NewsFragment;
import com.bettingtipsking.app.ui.home.notification.NotificationsFragment;

import com.bettingtipsking.app.ui.home.matches.MatchesFragment;
import com.bettingtipsking.app.ui.home.video.VideosFragment;
import com.bettingtipsking.app.viewmodel.NewsViewModel;
import com.bettingtipsking.app.viewmodel.PredictionsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.NewsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.PredictionsViewModelFactory;

import kotlinx.coroutines.Dispatchers;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    PredictionsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        PredictionsRepository repository = new PredictionsRepository(MatchesRetrofitHelper.INSTANCE.getInstance().create(MatchesService.class));
        viewModel = new ViewModelProvider(this, new PredictionsViewModelFactory(repository)).get(PredictionsViewModel.class);

        viewModel.getPredictions(813194);









    }
}