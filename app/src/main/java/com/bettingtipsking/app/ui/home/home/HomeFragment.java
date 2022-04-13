package com.bettingtipsking.app.ui.home.home;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;


import com.bettingtipsking.app.adapter.HomePredictionsAdapter;
import com.bettingtipsking.app.databinding.FragmentHomeBinding;
import com.bettingtipsking.app.model.HomelPredictionsModel;
import com.bettingtipsking.app.viewmodel.HomePredicationViewModel;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

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


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, 2019, 01, 01);

        sendRequest("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");

        viewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), homePredictionsModels -> {
            list = homePredictionsModels;
            adapter = new HomePredictionsAdapter(this, getContext(), list);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
        });

        binding.textAll.setOnClickListener(v -> {

        });

        binding.textCustom.setOnClickListener(v -> {
            datePickerDialog.show();
        });


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++;
        String customDate = (day<10?("0"+day):(day)+"-" +(month<10?("0"+month):(month)) + "-" +year);
        sendRequest("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + customDate + "&sort=-id");

    }

    private void sendRequest(String url){
        viewModel.getAllPredictions(url);

    }
}