package com.bettingtipsking.app.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.room.entity.FavouritLeaguesEntity;
import com.bettingtipsking.app.Helper.callback.FavouritLeagueInterfaceCallBack;
import com.bettingtipsking.app.model.FavouritLeagueModel;
import com.bettingtipsking.app.adapter.FavouriteLeaguesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityFavouriteLeaguesBinding;
import com.bettingtipsking.app.model.league.LeagueModel;
import com.bettingtipsking.app.ui.home.HomeActivity;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FavouriteLeaguesActivity extends AppCompatActivity implements FavouritLeagueInterfaceCallBack {

    ActivityFavouriteLeaguesBinding binding;
    LeagueViewModel viewModel;
    FavouriteLeaguesAdapter adapter;
    List<LeagueModel> list;
    List<Integer> listIDs;
    List<FavouritLeagueModel> favouriteLeagueModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_leagues);
        viewModel = new ViewModelProvider(this, new LeagueViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class),getApplication())).get(LeagueViewModel.class);

        list = new ArrayList<>();
        listIDs = new ArrayList<>();
        favouriteLeagueModelList = new ArrayList<>();
        viewModel.getLeagues();


        adapter = new FavouriteLeaguesAdapter(FavouriteLeaguesActivity.this, favouriteLeagueModelList, this);
        binding.recyclerViewFollowTeams.setLayoutManager(new LinearLayoutManager(FavouriteLeaguesActivity.this));
        binding.recyclerViewFollowTeams.setAdapter(adapter);


        viewModel.getAllLocalFavouritLeagues().observe(this, favLeagues -> {
            for (FavouritLeaguesEntity model : favLeagues) {
                listIDs.add(model.getId());
            }
        });

        viewModel.getLeaguesLiveData().observe(this, leagueModel -> {

            for (int i = 0; i < leagueModel.getResponse().size(); i++) {
                favouriteLeagueModelList.add(new FavouritLeagueModel(leagueModel.getResponse().get(i).getLeague().getId(), leagueModel.getResponse().get(i).getLeague().getName(), leagueModel.getResponse().get(i).getLeague().getType(), leagueModel.getResponse().get(i).getLeague().getLogo(), leagueModel.getResponse().get(i).getCountry().getName(), 0));
            }

            for (int i = 0; i < favouriteLeagueModelList.size(); i++) {
                if (listIDs.contains(favouriteLeagueModelList.get(i).getId()))
                    favouriteLeagueModelList.get(i).setFavStatus(0);
            }

            adapter.notifyDataSetChanged();
        });


        binding.appCompatButtonSend.setOnClickListener(v -> {
            QuickHelp.goToActivityAndFinish(this, HomeActivity.class);
        });
    }
    @Override
    public void favouritClick(int position) {
        if (favouriteLeagueModelList.get(position).getFavStatus() == 0) {
            favouriteLeagueModelList.get(position).setFavStatus(1);
            viewModel.deleteLocalFavouritLeague(new FavouritLeaguesEntity(favouriteLeagueModelList.get(position).getId(),favouriteLeagueModelList.get(position).getName(),favouriteLeagueModelList.get(position).getType(),favouriteLeagueModelList.get(position).getLogo()));
            adapter.notifyItemChanged(position);
        } else {
            favouriteLeagueModelList.get(position).setFavStatus(0);
            viewModel.insertLocalFavouritLeague(new FavouritLeaguesEntity(favouriteLeagueModelList.get(position).getId(),favouriteLeagueModelList.get(position).getName(),favouriteLeagueModelList.get(position).getType(),favouriteLeagueModelList.get(position).getLogo()));
            adapter.notifyItemChanged(position);
        }
    }


}