package com.bettingtipsking.app.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bettingtipsking.app.Helper.adapter_interfaces.ClickCallBack;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.favLeague.FavLeagueModel;
import com.bettingtipsking.app.Room.favLeague.FavLeagueViewModel;
import com.bettingtipsking.app.Room.favLeague.FavLeagues;
import com.bettingtipsking.app.adapter.FavouriteLeaguesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityFavouriteLeaguesBinding;

import com.bettingtipsking.app.model.league.LeagueModel;
import com.bettingtipsking.app.model.league.Response;
import com.bettingtipsking.app.viewmodel.LeagueViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LeagueViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FavouriteLeaguesActivity extends AppCompatActivity implements ClickCallBack {

    ActivityFavouriteLeaguesBinding binding;
    LeagueViewModel viewModel;
    FavLeagueViewModel favLeagueViewModel;
    List<LeagueModel> list;
    List<Response> responseList;
    List<FavLeagueModel> favouriteLeagueModelList;
    FavouriteLeaguesAdapter adapter;
    List<Integer> listIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_leagues);
        list = new ArrayList<>();
        responseList = new ArrayList<>();
        listIDs = new ArrayList<>();
        favouriteLeagueModelList = new ArrayList<>();
        viewModel = new ViewModelProvider(this, new LeagueViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(LeagueViewModel.class);
        favLeagueViewModel = new ViewModelProvider(this).get(FavLeagueViewModel.class);
        viewModel.getLeague();

        favLeagueViewModel.getFavLeague().observe(this, favLeagues1 -> {
            for (FavLeagues model : favLeagues1){
                listIDs.add(model.getId());
            }
        });

        viewModel.getData().observe(this, leagueModel -> {
            for (int i=0; i<leagueModel.getResponse().size(); i++){
                favouriteLeagueModelList.add(new FavLeagueModel(leagueModel.getResponse().get(i).getLeague().getId(),
                        leagueModel.getResponse().get(i).getLeague().getName(),
                        leagueModel.getResponse().get(i).getLeague().getType(),
                        leagueModel.getResponse().get(i).getLeague().getLogo(),
                        leagueModel.getResponse().get(i).getCountry().getName(),
                        0));
            }

            for (int i=0; i<favouriteLeagueModelList.size();i++){
                if (listIDs.contains(leagueModel.getResponse().get(i).getLeague().getId())){
                    favouriteLeagueModelList.get(i).setFavStatus(1);
                }else {
                    favouriteLeagueModelList.get(i).setFavStatus(0);
                }
            }

            responseList = leagueModel.getResponse();
            System.out.println("rlist " + leagueModel.getResponse());
            adapter = new FavouriteLeaguesAdapter(FavouriteLeaguesActivity.this, favouriteLeagueModelList,this);
            binding.recyclerViewFollowTeams.setLayoutManager(new LinearLayoutManager(FavouriteLeaguesActivity.this));
            binding.recyclerViewFollowTeams.setAdapter(adapter);


        });

    }

    @Override
    public void favStatus(int position) {
        if (favouriteLeagueModelList.get(position).getFavStatus()==0){
            favouriteLeagueModelList.get(position).setFavStatus(1);
            adapter.notifyItemChanged(position);
        }else {
            favouriteLeagueModelList.get(position).setFavStatus(0);
            adapter.notifyItemChanged(position);
        }
    }
}