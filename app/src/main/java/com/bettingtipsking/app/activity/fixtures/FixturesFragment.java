package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.FIXTURES;
import static com.bettingtipsking.app.Helper.HelperClass.FIXTURE_DETAIL_MODEL;
import static com.bettingtipsking.app.Helper.HelperClass.FIXTURE_MODEL;
import static com.bettingtipsking.app.Helper.HelperClass.HOST;
import static com.bettingtipsking.app.Helper.HelperClass.KEY;
import static com.bettingtipsking.app.Helper.HelperClass.REVIEW_TIME;
import static com.bettingtipsking.app.Helper.HelperClass.SHARED_PREFERENCE;
import static com.bettingtipsking.app.Helper.HelperClass.SUBSCRIPTION_DATE;
import static com.bettingtipsking.app.Helper.HelperClass.SUBSCRIPTION_PACKAGE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.activity.fixtures.adapters.FixturesAdapter;
import com.bettingtipsking.app.activity.fixtures.model.FixturesModel;
import com.bettingtipsking.app.activity.fixtures.model.MatchDetailsModel;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.R;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class FixturesFragment extends Fragment implements ItemClickListener, DatePickerListener {

    ProgressBar progressBar;
    RequestQueue queue;
    TextView txt_info;
    RelativeLayout rlMatches;
    RecyclerView rvFixtures;
    FixturesAdapter adapter;
    List<FixturesModel> fixturesList = new ArrayList<>();
    List<FixturesModel> fixturesListCopy = new ArrayList<>();
    Map<Integer, Integer> fixturesMap = new  HashMap<>();
    String currentDate = "";
    EditText search_et;
    boolean isValid = false;
    boolean liveMatch = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixtures, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view){

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        liveMatch = false;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentDate = currentYear+"-"+String.format("%02d", currentMonth)+"-"+String.format("%02d", currentDay);

        rlMatches = view.findViewById(R.id.rl_matches);
        search_et = view.findViewById(R.id.search_et);
        HorizontalPicker picker = (HorizontalPicker) view.findViewById(R.id.datePicker);
        // initialize it and attach a listener
        picker.setListener(this).init();
        progressBar = view.findViewById(R.id.progressBar);
        queue = Volley.newRequestQueue(requireActivity());
        txt_info = view.findViewById(R.id.txt_info);
        rvFixtures = view.findViewById(R.id.fixtures_rv);
        adapter = new FixturesAdapter(getContext(), fixturesList,this, FIXTURES);
        rvFixtures.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFixtures.setAdapter(adapter);
        getAllFixtures();
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    sortProductsListBySearch(s.toString());
                } else {
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        rlMatches.setOnClickListener(view1 -> {
            TextView tvAction = view.findViewById(R.id.action_tv);
            String action = tvAction.getText().toString();
            if (action.equals(getString(R.string.live_matches))){
                //live
                picker.setVisibility(View.GONE);
                liveMatch = true;
                tvAction.setText(getString(R.string.all_matches));
            }else{
                picker.setVisibility(View.VISIBLE);
                liveMatch = false;
                tvAction.setText(getString(R.string.live_matches));
            }
            getAllFixtures();
        });
    }

    @Override
    public void onStart() {
        isSubValid();
//        Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    public void isSubValid(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        String subPackage = sharedPreferences.getString(SUBSCRIPTION_PACKAGE, "null");
        String subDate = sharedPreferences.getString(SUBSCRIPTION_DATE, "0");
        if (subPackage.equalsIgnoreCase("null") && subDate.equalsIgnoreCase("null")){
            isValid = false;
        }else {
            if (subPackage.equals(Config.SUBS_1_WEEK)){
                long difference = new Date().getTime() - Long.parseLong(subDate);
                if (difference > (7 * 24 * 60 * 60 * 1000)){
                    isValid = false;
                }else{
                    isValid = true;
                }
            }else if (subPackage.equals(Config.SUBS_1_MONTH)){
                long difference = new Date().getTime() - Long.parseLong(subDate);
                if (difference > (30L * 24 * 60 * 60 * 1000)){
                    isValid = false;
                }else{
                    isValid = true;
                }
            }else if (subPackage.equals(Config.SUBS_3_MONTHS)){
                long difference = new Date().getTime() - Long.parseLong(subDate);
                if (difference > (90L * 24 * 60 * 60 * 1000)){
                    isValid = false;
                }else{
                    isValid = true;
                }
            }else if (subPackage.equals(Config.SUBS_6_MONTHS)){
                long difference = new Date().getTime() - Long.parseLong(subDate);
                if (difference > (180L * 24 * 60 * 60 * 1000)){
                    isValid = false;
                }else{
                    isValid = true;
                }
            }
        }
    }

    private void getAllFixtures() {
        Log.e("currentDate::", currentDate);
        String url;
        if (liveMatch) {
            Log.e("getAllFixtures::", "liveMatch");
            url = "https://api-football-v1.p.rapidapi.com/v3/fixtures?live=all";
        }else {
            Log.e("getAllFixtures::", currentDate);
            url = "https://api-football-v1.p.rapidapi.com/v3/fixtures?date=" + currentDate;
        }
        progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            fixturesList.clear();
                            fixturesListCopy.clear();
                            fixturesMap.clear();
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
                                fixturesListCopy.addAll(fixturesList);
                                txt_info.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                txt_info.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Expection! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
//                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(getContext(), "Error Responce " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onItemClicked(String parentPosition, Object obj) {
        FixturesModel model = fixturesList.get(Integer.parseInt(parentPosition));
        FixturesModel fixturesModel = new FixturesModel(model.getLeagueId(), model.getLeagueName(), model.getLeagueCountry(), model.getLeagueLogo(), model.getLeagueFlag(), model.getLeagueSeason(), model.getLeagueRound(), new ArrayList<>());
        if (isValid){
            startActivity(
                    new Intent(getActivity(), FixtureDetailsActivity.class)
                            .putExtra(FIXTURE_MODEL, fixturesModel)
                            .putExtra(FIXTURE_DETAIL_MODEL, (MatchDetailsModel)obj)
            );

        }else{
            startActivity(new Intent(getActivity(), SubscriptionsActivity.class));
            /*startActivity(
                    new Intent(getActivity(), FixtureDetailsActivity.class)
                            .putExtra(FIXTURE_MODEL, fixturesModel)
                            .putExtra(FIXTURE_DETAIL_MODEL, (MatchDetailsModel)obj)
            );*/
        }

    }

    public void sortProductsListBySearch(String searchQuery){
        fixturesList.clear();
        if (!searchQuery.isEmpty()) {
            if (fixturesListCopy.size() > 0) {
                for (int index=0; index<fixturesListCopy.size(); index++) {
                    /*if (fixturesListCopy.get(index).getLeagueName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        fixturesList.add(fixturesListCopy.get(index));
                    }*/
                    FixturesModel fixturesModelCopy = fixturesListCopy.get(index);
                    FixturesModel fixturesmodel;
                    List<MatchDetailsModel> matchesList = new ArrayList<>();
                    for (int ind=0; ind<fixturesModelCopy.getMatchesList().size(); ind++) {
                        if (fixturesModelCopy.getMatchesList().get(ind).getTeamHomeName().toLowerCase().contains(searchQuery.toLowerCase()) || fixturesModelCopy.getMatchesList().get(ind).getTeamAwayName().toLowerCase().contains(searchQuery.toLowerCase())) {
                            matchesList.add(fixturesModelCopy.getMatchesList().get(ind));
                        }
                    }
                    if (matchesList.size() > 0) {
                        fixturesmodel = new FixturesModel(fixturesModelCopy.getLeagueId(), fixturesModelCopy.getLeagueName(), fixturesModelCopy.getLeagueCountry(), fixturesModelCopy.getLeagueLogo(), fixturesModelCopy.getLeagueFlag(), fixturesModelCopy.getLeagueSeason(), fixturesModelCopy.getLeagueRound(), matchesList);
                        fixturesList.add(fixturesmodel);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        } else {
            fixturesList.addAll(fixturesListCopy);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        int year = dateSelected.getYear();
        int month = dateSelected.getMonthOfYear();
        int day = dateSelected.getDayOfMonth();
        currentDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        getAllFixtures();
    }
}