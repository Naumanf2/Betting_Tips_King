package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.adapter.SquadAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;

import com.bettingtipsking.app.databinding.FragmentSquadBinding;
import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.squad.Player;
import com.bettingtipsking.app.model.squad.Response;
import com.bettingtipsking.app.viewmodel.H2HViewModel;
import com.bettingtipsking.app.viewmodel.SquadViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.H2HModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.SquadViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SquadFragment extends Fragment {

    FragmentSquadBinding binding;
    SquadViewModel viewModel;
    SquadAdapter adapter;
    List<Player> list;

    int team_home_id;
    int team_away_id;

    public SquadFragment() {
    }
    public SquadFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
        this.team_home_id = team_home_id;
        this.team_away_id = team_away_id;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSquadBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new SquadViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(SquadViewModel.class);
        list = new ArrayList<>();

        viewModel.getSquad(team_home_id);

        viewModel.getMutableSquadData().observe(getViewLifecycleOwner(), squadModel -> {
            list.clear();
            List<Response> responses = squadModel.getResponse();
            if (!responses.isEmpty() && responses.size() > 0) {
                list = squadModel.getResponse().get(0).getPlayers();
                adapter = new SquadAdapter(getContext(), list);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerView.setAdapter(adapter);
            }

        });

        viewModel.getMutableProgressData().observe(getViewLifecycleOwner(),integer -> {
            if (integer==0)
                binding.progressBar.setVisibility(View.VISIBLE);
            else if (integer==1)
                binding.progressBar.setVisibility(View.GONE);

        });



        binding.textHome.setOnClickListener(v -> {
            //todo change the team id
            viewModel.getSquad(team_home_id);

        });

        binding.textAway.setOnClickListener(v -> {
            //todo change the team id
            viewModel.getSquad(team_away_id);

        });

        return binding.getRoot();
    }


}