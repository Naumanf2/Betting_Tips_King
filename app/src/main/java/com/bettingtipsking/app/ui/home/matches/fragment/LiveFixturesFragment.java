package com.bettingtipsking.app.ui.home.matches.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.adapter.MatchesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentLiveFixturesBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Fixture;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bettingtipsking.app.model.fixtures.League;
import com.bettingtipsking.app.model.fixtures.Response;
import com.bettingtipsking.app.model.fixtures.Score;
import com.bettingtipsking.app.model.fixtures.Teams;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.bettingtipsking.app.viewmodel.LiveFixturesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LiveFixturesViewModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LiveFixturesFragment extends Fragment implements ItemClickListener {
    FragmentLiveFixturesBinding binding;
    LiveFixturesViewModel viewModel;
    FixturesAdapter adapter;
    MatchesAdapter matchesAdapter;
    Map<Integer, Integer> map;
    List<com.bettingtipsking.app.model.FinalFixturesModel> list;
    List<com.bettingtipsking.app.model.FinalFixturesModel> listCopy;
    List<FinalMatchesModel> matchesModelList;
    boolean vis = false;


    public LiveFixturesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLiveFixturesBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, new LiveFixturesViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(LiveFixturesViewModel.class);
        viewModel.getLiveFixtures("all");

        map = new HashMap<>();
        list = new ArrayList<>();
        listCopy = new ArrayList<>();
        matchesModelList = new ArrayList<>();

        binding.recyclerView.setVisibility(View.VISIBLE);

        adapter = new FixturesAdapter(getContext(), list, this,false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        matchesAdapter = new MatchesAdapter(getContext(), matchesModelList, this,false);
        binding.matchesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.matchesRecyclerView.setAdapter(matchesAdapter);

        viewModel.getLiveMatchLiveData().observe(getViewLifecycleOwner(), fixturesModel -> {
            if (fixturesModel != null) {
                for (int i = 0; i < fixturesModel.getResponse().size(); i++) {
                    List<Response> response = fixturesModel.getResponse();
                    Fixture fixture = fixturesModel.getResponse().get(i).getFixture();
                    League league = response.get(i).getLeague();
                    Goals goals = fixturesModel.getResponse().get(i).getGoals();
                    Score score = fixturesModel.getResponse().get(i).getScore();
                    Teams teams = fixturesModel.getResponse().get(i).getTeams();

                    com.bettingtipsking.app.model.FinalFixturesModel finalFixturesModel = new com.bettingtipsking.app.model.FinalFixturesModel(league, new ArrayList<>());
                    FinalMatchesModel finalMatchDetailsModel = new FinalMatchesModel(fixture, league, goals, score, teams);
                    if (!map.containsKey(league.getId())) {
                        list.add(finalFixturesModel);
                        listCopy.add(finalFixturesModel);
                        map.put(league.getId(), list.indexOf(finalFixturesModel));
                    }
                    list.get(map.get(league.getId())).getMatches().add(finalMatchDetailsModel);
                    listCopy.get(map.get(league.getId())).getMatches().add(finalMatchDetailsModel);
                    matchesModelList.add(finalMatchDetailsModel);
                }

                if (list != null && !list.isEmpty()) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.matchesRecyclerView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    matchesAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        binding.editTextSearch.setOnClickListener(v -> {

        });

        return binding.getRoot();

    }

    private void filter(String toString) {
        if (toString != null && !toString.isEmpty()) {
            if (toString.length() > 1) {
                if (!vis) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.matchesRecyclerView.setVisibility(View.VISIBLE);
                }

                List<FinalMatchesModel> filterMatchesModelList = new ArrayList<>();
                for (int i=0; i<matchesModelList.size();i++){
                    if (matchesModelList.get(i).getTeams().getHome().getName().toLowerCase().contains(toString) ||matchesModelList.get(i).getTeams().getAway().getName().toLowerCase().contains(toString)){
                        filterMatchesModelList.add(matchesModelList.get(i));
                    }
                }
                matchesAdapter.filtredFist(filterMatchesModelList);
                vis = true;

            } else {
                vis = false;
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.matchesRecyclerView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onItemClicked(int position, FinalMatchesModel finalFixturesModel) {
        Intent intent = new Intent(getContext(), MatchDetailsActivity.class);
        intent.putExtra("fixture_id", finalFixturesModel.getFixture().getId());
        intent.putExtra("league_id", finalFixturesModel.getLeague().getId());
        intent.putExtra("team_home_id", finalFixturesModel.getTeams().getHome().getId());
        intent.putExtra("team_away_id", finalFixturesModel.getTeams().getAway().getId());
        startActivity(intent);
    }

    @Override
    public void onH2HIconClick(int teamHomeId, int teamAwayId) {
        TwoTeamsHeadToHeadFragment twoTeamsHeadToHeadFragment = new TwoTeamsHeadToHeadFragment();
        twoTeamsHeadToHeadFragment.setTaskId(new ComingFixturesFragment(), teamHomeId + "-" + teamAwayId);
        twoTeamsHeadToHeadFragment.show(getActivity().getSupportFragmentManager(), twoTeamsHeadToHeadFragment.getTag());
    }
}