package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.H2H;
import static com.bettingtipsking.app.Helper.HelperClass.HOST;
import static com.bettingtipsking.app.Helper.HelperClass.KEY;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.adapters.FixturesAdapter;
import com.bettingtipsking.app.activity.fixtures.model.FixturesModel;
import com.bettingtipsking.app.activity.fixtures.model.MatchDetailsModel;
import com.bettingtipsking.app.databinding.FragmentFixturesH2HBinding;
import com.bettingtipsking.app.databinding.FragmentPredictionBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixturesH2HFragment extends Fragment implements ItemClickListener {

    FixtureDetailsActivity parentContext;
    FragmentFixturesH2HBinding binding;

    FixturesAdapter adapter;
    List<FixturesModel> fixturesList = new ArrayList<>();
    Map<Integer, Integer> fixturesMap = new  HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFixturesH2HBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_fixtures_h2_h, container, false);
    }

    public void init(){
        adapter = new FixturesAdapter(getContext(), fixturesList,this, H2H);
        binding.fixturesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fixturesRv.setAdapter(adapter);
        getAllH2HFixtures();
    }

    private void getAllH2HFixtures() {
        String teamHome = parentContext.matchDetails.getTeamHomeId();
        String teamAway = parentContext.matchDetails.getTeamAwayId();
//        String url = "https://api-football-beta.p.rapidapi.com/fixtures/headtohead?h2h="+teamHome+"-"+teamAway+"&status=ft&last=50";
        String url = "https://api-football-v1.p.rapidapi.com/v3/fixtures/headtohead?h2h="+teamHome+"-"+teamAway+"&status=ft&last=50";
        Log.e("getAllH2HFixtures::", teamHome+"-"+teamAway);
        binding.progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        binding.progressBar.setVisibility(View.GONE);
                        try {
                            fixturesList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            Log.e("response::", response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject indexedJsonObject = array.getJSONObject(i);
                                JSONObject fixtureJsonObject = indexedJsonObject.getJSONObject("fixture");
                                JSONObject leagueJsonObject = indexedJsonObject.getJSONObject("league");
                                JSONObject teamsJsonObject = indexedJsonObject.getJSONObject("teams");
                                JSONObject goalsJsonObject = indexedJsonObject.getJSONObject("goals");
                                JSONObject scoreJsonObject = indexedJsonObject.getJSONObject("score");

                                int id = leagueJsonObject.getInt("id");
                                String name = leagueJsonObject.getString("name");
                                String country = leagueJsonObject.getString("country");
                                String logo = leagueJsonObject.getString("logo");
                                String flag = leagueJsonObject.getString("flag");
                                String season = leagueJsonObject.getString("season");
                                String round = leagueJsonObject.getString("round");

                                int fixtureId = fixtureJsonObject.getInt("id");
                                String fixtureReferee = fixtureJsonObject.getString("referee");
                                String fixtureTimezone = fixtureJsonObject.getString("timezone");
                                String fixtureDate = fixtureJsonObject.getString("date");
                                String fixtureTimestamp = fixtureJsonObject.getString("timestamp");

                                JSONObject fixturePeriodsJsonObject = fixtureJsonObject.getJSONObject("periods");
                                String fixturePeriodsFirst = fixturePeriodsJsonObject.getString("first");
                                String fixturePeriodsSecond = fixturePeriodsJsonObject.getString("second");

                                JSONObject fixtureVenueJsonObject = fixtureJsonObject.getJSONObject("venue");
                                String fixtureVenueId = fixtureVenueJsonObject.getString("id");
                                String fixtureVenueName = fixtureVenueJsonObject.getString("name");
                                String fixtureVenueCity = fixtureVenueJsonObject.getString("city");

                                JSONObject fixtureStatusJsonObject = fixtureJsonObject.getJSONObject("status");
                                String fixtureStatusLong = fixtureStatusJsonObject.getString("long");
                                String fixtureStatusShort = fixtureStatusJsonObject.getString("short");
                                String fixtureStatusElapsed = fixtureStatusJsonObject.getString("elapsed");

                                JSONObject teamHomeJsonObject = teamsJsonObject.getJSONObject("home");
                                String teamHomeId = teamHomeJsonObject.getString("id");
                                String teamHomeName = teamHomeJsonObject.getString("name");
                                String teamHomeLogo = teamHomeJsonObject.getString("logo");

                                JSONObject teamAwayJsonObject = teamsJsonObject.getJSONObject("away");
                                String teamAwayId = teamAwayJsonObject.getString("id");
                                String teamAwayName = teamAwayJsonObject.getString("name");
                                String teamAwayLogo = teamAwayJsonObject.getString("logo");

                                String goalsHome = goalsJsonObject.getString("home");
                                String goalsAway = goalsJsonObject.getString("away");

                                JSONObject scoreHalftimeJsonObject = scoreJsonObject.getJSONObject("halftime");
                                String scoreHalftimeHome = scoreHalftimeJsonObject.getString("home");
                                String scoreHalftimeAway = scoreHalftimeJsonObject.getString("away");

                                JSONObject scoreFullTimeJsonObject = scoreJsonObject.getJSONObject("fulltime");
                                String scoreFullTimeHome = scoreFullTimeJsonObject.getString("home");
                                String scoreFullTimeAway = scoreFullTimeJsonObject.getString("away");

                                JSONObject scoreExtraTimeJsonObject = scoreJsonObject.getJSONObject("extratime");
                                String scoreExtraTimeHome = scoreExtraTimeJsonObject.getString("home");
                                String scoreExtraTimeAway = scoreExtraTimeJsonObject.getString("away");

                                JSONObject PenaltyJsonObject = scoreJsonObject.getJSONObject("penalty");
                                String scorePenaltyHome = PenaltyJsonObject.getString("home");
                                String scorePenaltyAway = PenaltyJsonObject.getString("away");

                                FixturesModel fixturesModel = new FixturesModel(id, name, country, logo, flag, season, round, new ArrayList<>());
                                MatchDetailsModel matchDetailsModel = new MatchDetailsModel(fixtureId, fixtureReferee, fixtureTimezone, fixtureDate, fixtureTimestamp, fixturePeriodsFirst, fixturePeriodsSecond, fixtureVenueId, fixtureVenueName, fixtureVenueCity, fixtureStatusLong, fixtureStatusShort, fixtureStatusElapsed, teamHomeId, teamHomeName, teamHomeLogo, teamAwayId, teamAwayName, teamAwayLogo, goalsHome, goalsAway, scoreHalftimeHome, scoreHalftimeAway, scoreFullTimeHome, scoreFullTimeAway, scoreExtraTimeHome, scoreExtraTimeAway, scorePenaltyHome, scorePenaltyAway);

                                if (!fixturesMap.containsKey(id)){
                                    fixturesList.add(fixturesModel);
                                    fixturesMap.put(id, fixturesList.indexOf(fixturesModel));
                                }
                                fixturesList.get(fixturesMap.get(id)).getMatchesList().add(matchDetailsModel);

                            }

                            Log.e("fixturesList::", String.valueOf(fixturesList.size()));
                            Log.e("fixturesMap::", String.valueOf(fixturesMap.size()));
                            if (fixturesList != null && !fixturesList.isEmpty()) {
                                binding.txtInfo.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.txtInfo.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Expection! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.progressBar.setVisibility(View.GONE);
//                        Log.d("Error.Response", error.getMessage());
//                        Toast.makeText(getContext(), "Error Responce " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
//        queue.add(postRequest);
        MySingleton.getmInstance(getActivity()).addToRequestQueue(postRequest);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        parentContext = (FixtureDetailsActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onItemClicked(String position, Object obj) {

    }
}