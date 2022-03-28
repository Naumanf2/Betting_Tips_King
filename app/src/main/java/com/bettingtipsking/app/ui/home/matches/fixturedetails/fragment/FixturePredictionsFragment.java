package com.bettingtipsking.app.ui.home.matches.fixturedetails.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.ComparisonAdapter;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentFixturePredictionsBinding;
import com.bettingtipsking.app.databinding.FragmentH2HBinding;
import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.FinalPredictionsModel;
import com.bettingtipsking.app.model.predictions.Comparison;
import com.bettingtipsking.app.model.predictions.PredictionsModel;
import com.bettingtipsking.app.model.predictions.Response;
import com.bettingtipsking.app.viewmodel.H2HViewModel;
import com.bettingtipsking.app.viewmodel.PredictionsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.H2HModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.PredictionsViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FixturePredictionsFragment extends Fragment {
    FragmentFixturePredictionsBinding binding;
    PredictionsViewModel viewModel;
    ComparisonAdapter adapter;
    List<PredictionsModel> list;
    List<FinalPredictionsModel> finalPredictionsModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFixturePredictionsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, new PredictionsViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(PredictionsViewModel.class);
        //todo feature id
        viewModel.getPredictions(839509);

        list = new ArrayList<>();
        finalPredictionsModelList = new ArrayList<>();

        adapter = new ComparisonAdapter(getContext(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getPredictionsLiveData().observe(getViewLifecycleOwner(), predictionsModel -> {
            list.add(predictionsModel);

            List<Response> responses = predictionsModel.getResponse();


            for (int i = 0; i < list.size(); i++) {
              Comparison comparison = responses.get(i).component1();
                System.out.println("SISIS "+ comparison.component1() );

            }
            adapter.notifyDataSetChanged();
        });
        return binding.getRoot();
    }
}