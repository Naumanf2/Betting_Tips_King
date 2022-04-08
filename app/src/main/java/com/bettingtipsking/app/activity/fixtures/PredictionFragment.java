package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.HOST;
import static com.bettingtipsking.app.Helper.HelperClass.KEY;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.adapters.BindingMethodAdapter;
import com.bettingtipsking.app.activity.fixtures.adapters.StatsAdapter;
import com.bettingtipsking.app.activity.fixtures.model.StatsModel;
import com.bettingtipsking.app.databinding.FragmentPredictionBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictionFragment extends Fragment {

    FragmentPredictionBinding binding;
    FixtureDetailsActivity parentContext;
    List<StatsModel> statsModelList = new ArrayList<>();
    StatsAdapter adapter;

    public PredictionFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPredictionBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_prediction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StatsAdapter(getContext(), statsModelList);
        binding.statsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.statsRv.setAdapter(adapter);
        getMatchPrediction();
    }

    private void getMatchPrediction() {
        statsModelList.clear();
//        String url = "https://api-football-beta.p.rapidapi.com/predictions?fixture="+parentContext.matchDetails.getFixtureId();
        String url = "https://api-football-v1.p.rapidapi.com/v3/predictions?fixture="+parentContext.matchDetails.getFixtureId();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("predictions response::", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            JSONObject predictions = array.getJSONObject(0).getJSONObject("predictions");
                            JSONObject comparison = array.getJSONObject(0).getJSONObject("comparison");
                            JSONObject teams = array.getJSONObject(0).getJSONObject("teams");
                            JSONObject homeTeam = teams.getJSONObject("home");
                            JSONObject awayTeam = teams.getJSONObject("away");

                            JSONObject percentage = predictions.getJSONObject("percent");
                            String homePercentage = percentage.getString("home");
                            String awayPercentage = percentage.getString("away");
                            String draw = percentage.getString("draw");

                            binding.drawChancesTv.setText("Match Draw: "+draw);
                            binding.homePercentTv.setText(homePercentage);
                            binding.awayPercentTv.setText(awayPercentage);
                            binding.homeTeamTv.setText(homeTeam.getString("name"));
                            binding.awayTeamTv.setText(awayTeam.getString("name"));

                            int homeProg = Integer.parseInt(homePercentage.replace("%", ""));
                            int awayProg = Integer.parseInt(awayPercentage.replace("%", ""));
                            if (homeProg > awayProg){
                                binding.pbHome.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                binding.pbAway.setProgressTintList(ColorStateList.valueOf(Color.RED));
                            }else{
                                binding.pbHome.setProgressTintList(ColorStateList.valueOf(Color.RED));
                                binding.pbAway.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                            }
                            binding.pbHome.setProgress(homeProg);
                            binding.pbAway.setProgress(awayProg);

                            BindingMethodAdapter.loadImage(binding.homeFlagIv, homeTeam.getString("logo"));
                            BindingMethodAdapter.loadImage(binding.awayFlagIv, awayTeam.getString("logo"));


                            JSONObject form = comparison.getJSONObject("form");
                            JSONObject att = comparison.getJSONObject("att");
                            JSONObject def = comparison.getJSONObject("def");
                            JSONObject poisson_distribution = comparison.getJSONObject("poisson_distribution");
                            JSONObject h2h = comparison.getJSONObject("h2h");
                            JSONObject goals = comparison.getJSONObject("goals");
                            JSONObject total = comparison.getJSONObject("total");

                            String formAScore = form.getString("home");
                            String formBScore = form.getString("away");
                            String attAScore = att.getString("home");
                            String attBScore = att.getString("away");
                            String defAScore = def.getString("home");
                            String defBScore = def.getString("away");
                            String poissonDistributionAScore = poisson_distribution.getString("home");
                            String poissonDistributionBScore = poisson_distribution.getString("away");
                            String h2hAScore = h2h.getString("home");
                            String h2hBScore = h2h.getString("away");
                            String goalsAScore = goals.getString("home");
                            String goalsBScore = goals.getString("away");
                            String totalAScore = total.getString("home");
                            String totalBScore = total.getString("away");

                            if (formAScore.equalsIgnoreCase("null"))
                                formAScore = "0";
                            if (formBScore.equalsIgnoreCase("null"))
                                formBScore = "0";
                            if (attAScore.equalsIgnoreCase("null"))
                                attAScore = "0";
                            if (attBScore.equalsIgnoreCase("null"))
                                attBScore = "0";
                            if (defAScore.equalsIgnoreCase("null"))
                                defAScore = "0";
                            if (defBScore.equalsIgnoreCase("null"))
                                defBScore = "0";
                            if (poissonDistributionAScore.equalsIgnoreCase("null"))
                                poissonDistributionAScore = "0";
                            if (poissonDistributionBScore.equalsIgnoreCase("null"))
                                poissonDistributionBScore = "0";
                            if (h2hAScore.equalsIgnoreCase("null"))
                                h2hAScore = "0";
                            if (h2hBScore.equalsIgnoreCase("null"))
                                h2hBScore = "0";
                            if (goalsAScore.equalsIgnoreCase("null"))
                                goalsAScore = "0";
                            if (goalsBScore.equalsIgnoreCase("null"))
                                goalsBScore = "0";
                            if (totalAScore.equalsIgnoreCase("null"))
                                totalAScore = "0";
                            if (totalBScore.equalsIgnoreCase("null"))
                                totalBScore = "0";

                            StatsModel model1 = new StatsModel("for", formAScore, formBScore);
                            StatsModel model2 = new StatsModel("att", attAScore, attBScore);
                            StatsModel model3 = new StatsModel("def", defAScore, defBScore);
                            StatsModel model4 = new StatsModel("poisson distribution", poissonDistributionAScore, poissonDistributionBScore);
                            StatsModel model5 = new StatsModel("h2h", h2hAScore, h2hBScore);
                            StatsModel model6 = new StatsModel("goals", goalsAScore, goalsBScore);
                            StatsModel model7 = new StatsModel("total", totalAScore, totalBScore);
                            statsModelList.add(model1);
                            statsModelList.add(model2);
                            statsModelList.add(model3);
                            statsModelList.add(model4);
                            statsModelList.add(model5);
                            statsModelList.add(model6);
                            statsModelList.add(model7);
                            adapter.notifyDataSetChanged();

                            binding.winnerTv.setText("Winner:"+predictions.getJSONObject("winner").getString("name"));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("x-rapidapi-host", HOST);
                params.put("x-rapidapi-key", KEY);
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(postRequest);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parentContext = (FixtureDetailsActivity) context;
    }
}