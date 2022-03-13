package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.HOST;
import static com.bettingtipsking.app.Helper.HelperClass.KEY;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.adapters.BindingMethodAdapter;
import com.bettingtipsking.app.activity.fixtures.adapters.EventsAdapter;
import com.bettingtipsking.app.activity.fixtures.adapters.StatsAdapter;
import com.bettingtipsking.app.activity.fixtures.model.StatsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bettingtipsking.app.databinding.FragmentLinupPlayersBinding;
import com.bettingtipsking.app.databinding.FragmentStatsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsFragment extends Fragment {
    FragmentStatsBinding binding;
    FixtureDetailsActivity parentContext;
    List<StatsModel> statsModelList = new ArrayList<>();
    StatsAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    public void init(){
        adapter = new StatsAdapter(getContext(), statsModelList);
        binding.statsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.statsRv.setAdapter(adapter);
        getStats();
    }

    private void getStats() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.txtInfo.setVisibility(View.GONE);
        Log.e("getStats::", String.valueOf(parentContext.matchDetails.getFixtureId()));
        String url = "https://api-football-v1.p.rapidapi.com/v3/fixtures?id="+parentContext.matchDetails.getFixtureId();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            binding.progressBar.setVisibility(View.GONE);
                            statsModelList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            JSONArray statistics = array.getJSONObject(0).getJSONArray("statistics");

                            Log.e("response::", response);

                            JSONObject teamAObj = statistics.getJSONObject(0).getJSONObject("team");
                            JSONObject teamBObj = statistics.getJSONObject(1).getJSONObject("team");

                            binding.team1TitleTv.setText(teamAObj.getString("name"));
                            binding.team2TitleTv.setText(teamBObj.getString("name"));
                            BindingMethodAdapter.loadImage(binding.team1FlagIv, teamAObj.getString("logo"));
                            BindingMethodAdapter.loadImage(binding.team2FlagIv, teamBObj.getString("logo"));


                            JSONArray statisticsTeamA = statistics.getJSONObject(0).getJSONArray("statistics");
                            JSONArray statisticsTeamB = statistics.getJSONObject(1).getJSONArray("statistics");
                            for (int i = 0; i < statisticsTeamA.length(); i++) {
                                JSONObject teamAJsonObject = statisticsTeamA.getJSONObject(i);
                                JSONObject teamBJsonObject = statisticsTeamB.getJSONObject(i);

                                String title = teamAJsonObject.getString("type");
                                String teamAScore = teamAJsonObject.getString("value");
                                String teamBScore = teamBJsonObject.getString("value");

                                if (teamAScore.equalsIgnoreCase("null"))
                                    teamAScore = "0";
                                if (teamBScore.equalsIgnoreCase("null"))
                                    teamBScore = "0";

                                StatsModel model = new StatsModel(title, teamAScore, teamBScore);
                                statsModelList.add(model);

                            }
                            if (statsModelList.size() > 0){
                                binding.txtInfo.setVisibility(View.GONE);
                            }else{
                                binding.txtInfo.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            binding.txtInfo.setVisibility(View.VISIBLE);
//                            Toast.makeText(getContext(), "Exception! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.txtInfo.setVisibility(View.VISIBLE);
//                        Log.d("Error.Response", error.getMessage());
//                        Toast.makeText(getContext(), "Error Response " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        parentContext = (FixtureDetailsActivity) context;
        super.onAttach(context);
    }
}