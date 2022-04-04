package com.bettingtipsking.app.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityFavouriteLeaguesBinding;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.model.league.LeagueModel;
import com.bettingtipsking.app.model.league.Response;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.LiveFixturesViewModel;
import com.bettingtipsking.app.viewmodel.TeamsInfoViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LiveFixturesViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.TeamsInfoViewModelFactory;

import java.util.List;

public class FavouriteLeaguesActivity extends AppCompatActivity {

    ActivityFavouriteLeaguesBinding binding;
    LeagueViewModel viewModel;
    List<Response> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_leagues);

        viewModel = new ViewModelProvider(this, new LeagueViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(LeagueViewModel.class);
        viewModel.getLeague();

        viewModel.getData().observe(this, leagueModel -> {
            list = leagueModel.getResponse();

            //list.get(postion).getLeague().getLogo();


        });

    }
}