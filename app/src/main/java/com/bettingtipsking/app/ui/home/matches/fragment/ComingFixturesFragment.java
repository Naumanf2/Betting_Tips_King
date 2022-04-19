package com.bettingtipsking.app.ui.home.matches.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.adapter.MatchesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentComingFixturesBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Fixture;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bettingtipsking.app.model.fixtures.League;
import com.bettingtipsking.app.model.fixtures.Response;
import com.bettingtipsking.app.model.fixtures.Score;
import com.bettingtipsking.app.model.fixtures.Teams;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.bettingtipsking.app.viewmodel.ComingFixturesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.ComingFixturesViewModelFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComingFixturesFragment extends Fragment implements ItemClickListener, DatePickerDialog.OnDateSetListener {
    FragmentComingFixturesBinding binding;
    ComingFixturesViewModel viewModel;
    FixturesAdapter adapter;
    MatchesAdapter matchesAdapter;
    Map<Integer, Integer> map;
    List<com.bettingtipsking.app.model.FinalFixturesModel> list;
    List<FinalMatchesModel> matchesModelList;
    boolean vis = false;

    public ComingFixturesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentComingFixturesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new ComingFixturesViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(ComingFixturesViewModel.class);

        viewModel.getComingFixtures(getTomorrowDate(getTodayDate()));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, 2019, 01, 01);

        map = new HashMap<>();
        list = new ArrayList<>();
        matchesModelList = new ArrayList<>();

        binding.recyclerView.setVisibility(View.VISIBLE);

        adapter = new FixturesAdapter(getContext(), list, this, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        matchesAdapter = new MatchesAdapter(getContext(), matchesModelList, this, false);
        binding.matchesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.matchesRecyclerView.setAdapter(matchesAdapter);

        viewModel.getComingMatchLiveData().observe(getViewLifecycleOwner(), fixturesModel -> {
            map.clear();
            list.clear();
            matchesModelList.clear();

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
                        map.put(league.getId(), list.indexOf(finalFixturesModel));
                    }
                    list.get(map.get(league.getId())).getMatches().add(finalMatchDetailsModel);
                    matchesModelList.add(finalMatchDetailsModel);


                if (list != null && !list.isEmpty()) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.matchesRecyclerView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    matchesAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getMutableProgressComingFixturesData().observe(getViewLifecycleOwner(),integer -> {
            if (integer==0)
                binding.progressBar.setVisibility(View.VISIBLE);
            else if (integer==1)
                binding.progressBar.setVisibility(View.GONE);
        });

        binding.textOvermorrow.setOnClickListener(v -> {
            viewModel.getComingFixtures(getTomorrowDate(getTodayDate()));
        });

        binding.textOvermorrow.setOnClickListener(v -> {
            viewModel.getComingFixtures(getOvermorrowDate(getTodayDate()));
        });

        binding.textCustom.setOnClickListener(v -> {
            datePickerDialog.show();

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
                for (int i = 0; i < matchesModelList.size(); i++) {
                    if (matchesModelList.get(i).getTeams().getHome().getName().toLowerCase().contains(toString) || matchesModelList.get(i).getTeams().getAway().getName().toLowerCase().contains(toString)) {
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


    private String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTomorrowDate(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, +1);
            inputDate = format.format(c.getTime());
            Log.d("asd", "selected date : " + inputDate);

            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            inputDate = "";
        }
        return inputDate;
    }

    private String getOvermorrowDate(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, +2);
            inputDate = format.format(c.getTime());
            Log.d("asd", "selected date : " + inputDate);

            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            inputDate = "";
        }
        return inputDate;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++;
        String customDate = year + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + (day < 10 ? ("0" + day) : (day));
        viewModel.getComingFixtures(customDate);
    }
}