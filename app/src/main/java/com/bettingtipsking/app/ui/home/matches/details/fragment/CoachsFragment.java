package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.CoachesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentCoachsBinding;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.model.coach.CoachesModel;
import com.bettingtipsking.app.model.events.EventsModel;
import com.bettingtipsking.app.model.events.Response;
import com.bettingtipsking.app.viewmodel.CoachesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.CoachesViewModelFactory;
import com.bumptech.glide.Glide;

import java.util.List;


public class CoachsFragment extends Fragment {

    FragmentCoachsBinding binding;
    CoachesViewModel viewModel;

    int team_home_id;
    int team_away_id;

    public CoachsFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
        this.team_home_id = team_home_id;
        this.team_away_id = team_away_id;
    }

    public CoachsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoachsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new CoachesViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(CoachesViewModel.class);
        viewModel.getCoachA(team_home_id);
        viewModel.getCoachB(team_away_id);

        viewModel.getCoachesLiveDataA().observe(getViewLifecycleOwner(), coachesModel -> {

            List<com.bettingtipsking.app.model.coach.Response> list = coachesModel.getResponse();

            if (!list.isEmpty() && list.size() > 0) {

                binding.textCoachNameA.setText(list.get(0).getName());
                binding.textDateOfBirthA.setText("" + list.get(0).getBirth().getDate());
                binding.textAgeA.setText("age: " + list.get(0).getAge());
                binding.textPlaceA.setText("" + list.get(0).getNationality());
                binding.textTeamNameA.setText(list.get(0).getTeam().getName());
                Glide.with(getContext()).load(list.get(0).getTeam().getLogo()).into(binding.imageTeamLogoA);
            }
        });


        viewModel.getCoachesLiveDataB().observe(getViewLifecycleOwner(), coachesModel -> {
            List<com.bettingtipsking.app.model.coach.Response> list = coachesModel.getResponse();

            if (!list.isEmpty() && list.size() > 0) {
                binding.textCoachNameB.setText(list.get(0).getName());
                binding.textDateOfBirthB.setText("" + list.get(0).getBirth().getDate());
                binding.textAgeB.setText("age: " + list.get(0).getAge());
                binding.textPlaceB.setText("" + list.get(0).getNationality());
                binding.textTeamNameB.setText(list.get(0).getTeam().getName());
                Glide.with(getContext()).load(list.get(0).getTeam().getLogo()).into(binding.imageTeamLogoB);
            }

        });


        return binding.getRoot();
    }
}