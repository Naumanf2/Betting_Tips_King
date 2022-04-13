package com.bettingtipsking.app.ui.home.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bettingtipsking.app.adapter.HomePredictionsAdapter;
import com.bettingtipsking.app.databinding.FragmentHomeBinding;
import com.bettingtipsking.app.model.HomelPredictionsModel;
import com.bettingtipsking.app.viewmodel.HomePredicationViewModel;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DatePickerListener {

    private FragmentHomeBinding binding;
    private HomePredicationViewModel viewModel;
    private List<HomelPredictionsModel> list;
    private HomePredictionsAdapter adapter;
    public boolean dateCheck = false;

    public String finalDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomePredicationViewModel.class);

        list = new ArrayList<>();

        viewModel.getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");

        viewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), homePredictionsModels -> {
            list = homePredictionsModels;
            adapter = new HomePredictionsAdapter(this, getContext(), list);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
        });

        // Listener
        binding.horizontalPicker.setListener(this).init();
        dateCheck = false;


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDateSelected(DateTime dateSelected) {

        if (dateSelected != null) {
            dateCheck = true;
            // txt_filter.setVisibility(View.VISIBLE);
            String year = String.valueOf(dateSelected.getYear());
            String month = String.valueOf(dateSelected.getMonthOfYear());
            String day = String.valueOf(dateSelected.getDayOfMonth());
            if (Integer.valueOf(month) < 10) {
                month = "0" + month;
            }
            if (Integer.valueOf(day) < 10) {
                day = "0" + day;
            }
            finalDate = day + "." + month + "." + year;

        }
    }


}