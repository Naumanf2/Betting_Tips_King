package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.adapter.StatisticsAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;

import com.bettingtipsking.app.databinding.FragmentStatisticsBinding;
import com.bettingtipsking.app.databinding.FragmentStatsBinding;
import com.bettingtipsking.app.model.StatisticsModel;
import com.bettingtipsking.app.model.statistics.Statistic;
import com.bettingtipsking.app.viewmodel.FixturesStatisticsViewModel;
import com.bettingtipsking.app.viewmodel.PastFixturesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.FixturesStatisticsViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.PastFixturesViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    FragmentStatisticsBinding binding;
    FixturesStatisticsViewModel viewModel;

    StatisticsAdapter adapterTeamHome;
    StatisticsAdapter adapterTeamAway;

    List<StatisticsModel> listTeamHome;
    List<StatisticsModel> listTeamAway;

    public StatisticsFragment() {
    }

    public StatisticsFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new FixturesStatisticsViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(FixturesStatisticsViewModel.class);


        listTeamHome = new ArrayList<>();
        listTeamAway = new ArrayList<>();

        viewModel.getLiveFixtures(215662);

        adapterTeamHome = new StatisticsAdapter(getContext(), listTeamHome);
        binding.recyclerViewTeamHome.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerViewTeamHome.setAdapter(adapterTeamHome);

        adapterTeamAway = new StatisticsAdapter(getContext(), listTeamAway);
        binding.recyclerViewTeamAway.setLayoutManager(new GridLayoutManager(getContext(),3));
        binding.recyclerViewTeamAway.setAdapter(adapterTeamAway);

        viewModel.getLiveMatchLiveData().observe(getViewLifecycleOwner(), fixturesStatistics -> {



           List<Statistic> list1 = fixturesStatistics.getResponse().get(0).getStatistics();
            List<Statistic> list2 = fixturesStatistics.getResponse().get(1).getStatistics();

            for (int i = 0; i < list1.size(); i++) {
                listTeamHome.add(new StatisticsModel(fixturesStatistics.getResponse().get(0).getTeam().getId(), fixturesStatistics.getResponse().get(0).getTeam().getName(), fixturesStatistics.getResponse().get(0).getTeam().getLogo(), list1.get(i).getType(), "" + list1.get(i).getValue()));
            }

            for (int i = 0; i < list2.size(); i++) {
                listTeamHome.add(new StatisticsModel(fixturesStatistics.getResponse().get(1).getTeam().getId(), fixturesStatistics.getResponse().get(1).getTeam().getName(), fixturesStatistics.getResponse().get(1).getTeam().getLogo(), list2.get(i).getType(), "" + list2.get(i).getValue()));
            }

            adapterTeamHome.notifyDataSetChanged();
            adapterTeamAway.notifyDataSetChanged();
        });


        return binding.getRoot();

    }
}