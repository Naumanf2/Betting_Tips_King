package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.adapter.ComparisonAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentFixturePredictionsBinding;
import com.bettingtipsking.app.model.FinalPredictionsModel;
import com.bettingtipsking.app.model.predictions.Response;
import com.bettingtipsking.app.viewmodel.PredictionsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.PredictionsViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class FixturePredictionsFragment extends Fragment {
    FragmentFixturePredictionsBinding binding;
    PredictionsViewModel viewModel;
    ComparisonAdapter adapter;
    List<FinalPredictionsModel> list;
    int fixture_id;

    public FixturePredictionsFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
    this.fixture_id = fixture_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFixturePredictionsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, new PredictionsViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(PredictionsViewModel.class);
        //todo feature id
        viewModel.getPredictions(fixture_id);

        list = new ArrayList<>();

        adapter = new ComparisonAdapter(getContext(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getPredictionsLiveData().observe(getViewLifecycleOwner(), predictionsModel -> {

            binding.setPredictions(predictionsModel);

            List<Response> responses = predictionsModel.getResponse();
            String homeTeamName = responses.get(0).getTeams().getHome().getName();
            String homeTeamLogo = responses.get(0).getTeams().getHome().getLogo();
            String awayTeamName = responses.get(0).getTeams().getAway().getName();
            String awayTeamLogo = responses.get(0).getTeams().getAway().getLogo();

            FinalPredictionsModel model1 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Form", responses.get(0).getComparison().getForm().getHome(), responses.get(0).getComparison().getForm().getAway());
            FinalPredictionsModel model2 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Att", responses.get(0).getComparison().getAtt().getHome(), responses.get(0).getComparison().getAtt().getAway());
            FinalPredictionsModel model3 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Def", responses.get(0).getComparison().getDef().getHome(), responses.get(0).getComparison().getDef().getAway());
            FinalPredictionsModel model4 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Poisson Distribution", responses.get(0).getComparison().getPoisson_distribution().getHome(), responses.get(0).getComparison().getPoisson_distribution().getAway());
            FinalPredictionsModel model5 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "H2H", responses.get(0).getComparison().getH2h().getHome(), responses.get(0).getComparison().getH2h().getAway());
            FinalPredictionsModel model6 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Goals", responses.get(0).getComparison().getGoals().getHome(), responses.get(0).getComparison().getGoals().getAway());
            FinalPredictionsModel model7 = new FinalPredictionsModel(homeTeamName, homeTeamLogo, awayTeamName, awayTeamLogo, "Total", responses.get(0).getComparison().getTotal().getHome(), responses.get(0).getComparison().getTotal().getAway());

            list.add(model1);
            list.add(model2);
            list.add(model3);
            list.add(model4);
            list.add(model5);
            list.add(model6);
            list.add(model7);
            adapter.notifyDataSetChanged();
        });
        return binding.getRoot();
    }
}