package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.activity.fixtures.FixtureDetailsActivity;
import com.bettingtipsking.app.activity.fixtures.adapters.LineupPlayersAdapter;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bettingtipsking.app.databinding.FragmentLinupPlayersBinding;
import com.bettingtipsking.app.viewmodel.LineupViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineupPlayersFragment extends Fragment {
    FragmentLinupPlayersBinding binding;
    LineupViewModel viewModel;

    FixtureDetailsActivity parentContext;
    List<TeamWithPlayers> teamsWithPlayersList = new ArrayList<>();
    Map<Integer, String> playersMap = new HashMap<>();

    int fixture_id;

    public LineupPlayersFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
        this.fixture_id = fixture_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLinupPlayersBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LineupViewModel.class);

        viewModel.getLineup("https://api-football-v1.p.rapidapi.com/v3/fixtures?id="+fixture_id);
        /* getPlayers();*/

        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(),teamWithPlayers -> {
            if (teamWithPlayers!=null){
                teamsWithPlayersList = teamWithPlayers;
                init();
            }

        });

        return binding.getRoot();
    }

    public void init() {
        LineupPlayersAdapter adapterKeeper = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getGoalKeeper());
        binding.keeper1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.keeper1Rv.setAdapter(adapterKeeper);

        LineupPlayersAdapter adapterDefender = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getDefender());
        binding.defender1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.defender1Rv.setAdapter(adapterDefender);

        LineupPlayersAdapter adapterMidfielder = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getMidfielder());
        binding.midfielder1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.midfielder1Rv.setAdapter(adapterMidfielder);
        LineupPlayersAdapter adapterForward = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getForward());
        binding.forward1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.forward1Rv.setAdapter(adapterForward);

        LineupPlayersAdapter adapterKeeper2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getGoalKeeper());
        binding.keeper2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.keeper2Rv.setAdapter(adapterKeeper2);

        LineupPlayersAdapter adapterDefender2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getDefender());
        binding.defender2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.defender2Rv.setAdapter(adapterDefender2);

        LineupPlayersAdapter adapterMidfielder2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getMidfielder());
        binding.midfielder2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.midfielder2Rv.setAdapter(adapterMidfielder2);

        LineupPlayersAdapter adapterForward2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getForward());
        binding.forward2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.forward2Rv.setAdapter(adapterForward2);

        binding.teamTitleTv.setText(teamsWithPlayersList.get(0).getName());
        Glide.with(getActivity()).load(teamsWithPlayersList.get(0).getLogo()).into(binding.teamFlagIv);

        binding.coachNameTv.setText("Coach: " + teamsWithPlayersList.get(0).getCoachName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(0).getCoachPhoto())
                .into(binding.coachIv);

        binding.team2TitleTv.setText(teamsWithPlayersList.get(1).getName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(1).getLogo())
                .into(binding.team2FlagIv);
        binding.coach2NameTv.setText("Coach: " + teamsWithPlayersList.get(1).getCoachName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(1).getCoachPhoto())
                .into(binding.coach2Iv);


    }

}