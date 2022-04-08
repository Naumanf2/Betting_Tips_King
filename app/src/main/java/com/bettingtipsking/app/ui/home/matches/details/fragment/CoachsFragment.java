package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.CoachesAdapter;
import com.bettingtipsking.app.adapter.EventsAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentCoachsBinding;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.model.coach.CoachesModel;
import com.bettingtipsking.app.model.events.EventsModel;
import com.bettingtipsking.app.model.events.Response;
import com.bettingtipsking.app.viewmodel.CoachesViewModel;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.CoachesViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.EventsViewModelFactory;

import java.util.List;


public class CoachsFragment extends Fragment {

    FragmentCoachsBinding binding;
    CoachesViewModel viewModel;
    CoachesAdapter adapter;
    List<CoachesModel> list;
    List<Response> responseList;

    public CoachsFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoachsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new CoachesViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(CoachesViewModel.class);
        viewModel.getCoach(1954);

        viewModel.getCoachesLiveData().observe(getViewLifecycleOwner(),coachesModel -> {
            binding.setCoach(coachesModel);

        });


        binding.textHome.setOnClickListener(v -> {
            binding.textHome.setBackground(getResources().getDrawable(R.drawable.bg_dark_background));
            binding.textHome.setTextColor(getResources().getColor(R.color.white));
            binding.textAway.setBackground(getResources().getDrawable(R.drawable.bg_light_back_ground));
            binding.textAway.setTextColor(getResources().getColor(R.color.black));

            //todo call function
        });

        binding.textAway.setOnClickListener(v -> {
            binding.textAway.setBackground(getResources().getDrawable(R.drawable.bg_dark_background));
            binding.textAway.setTextColor(getResources().getColor(R.color.white));
            binding.textHome.setBackground(getResources().getDrawable(R.drawable.bg_light_back_ground));
            binding.textHome.setTextColor(getResources().getColor(R.color.black));

            //todo call function

        });
        return binding.getRoot();
    }
}