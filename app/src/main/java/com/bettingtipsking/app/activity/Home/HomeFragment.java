package com.bettingtipsking.app.activity.Home;

import static com.bettingtipsking.app.Helper.HelperClass.getCurrentDate;
import static com.bettingtipsking.app.Helper.HelperClass.isSubValid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bettingtipsking.app.activity.Home.Adapter.AllPredictionsAdapter;
import com.bettingtipsking.app.Helper.PredictionsInterface;
import com.bettingtipsking.app.Model.AllPredictionsModel;
import com.bettingtipsking.app.PreViewHolder;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.PendingPredictions;
import com.bettingtipsking.app.Room.Predictions;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements PredictionsInterface, DatePickerListener {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView txt_info;
    TextView txt_filter;
    Spinner spinner;
    AllPredictionsAdapter adapter;
    RequestQueue queue;

    List<AllPredictionsModel> list;

    PreViewHolder preViewHolder;

    List<Integer> favouritList = new ArrayList<>();
    List<Integer> todayPendingList = new ArrayList<>();
    List<String> pendingList = new ArrayList<>();
    List<Integer> idList = new ArrayList<>();
    List<PendingPredictions> todayPendingPredictionList = new ArrayList<>();

    String gameCheck = "all";
    public boolean dateCheck = false;

    public String finalDate;
    String finalTodayDate;
    public boolean isBetEnabled = true;
    public String betLink = "https://affpa.top/L?tag=d_1377247m_97c_&site=1377247&ad=97";
    DatabaseReference RootRef;
    public boolean isSubValid = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dateCheck = false;
        isSubValid = true;
        if (!isSubValid(getActivity())) {
            isSubValid = false;
            AdView mAdView = new AdView(getActivity());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(getString(R.string.admob_banner_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            if(mAdView.getAdSize() != null || mAdView.getAdUnitId() != null)
                mAdView.loadAd(adRequest);
            // else Log state of adsize/adunit
            ((LinearLayout)view.findViewById(R.id.adView)).addView(mAdView);
          /*  AdView adView = view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);*/
        }

        queue = Volley.newRequestQueue(getContext());
        preViewHolder = new ViewModelProvider(this).get(PreViewHolder.class);

        list = new ArrayList<>();
        RootRef = FirebaseDatabase.getInstance().getReference();
        getBetReference();

        spinner = view.findViewById(R.id.spinner);
        txt_filter = view.findViewById(R.id.txt_filter);
        txt_info = view.findViewById(R.id.txt_info);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new AllPredictionsAdapter(this, getContext(), list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        HorizontalPicker picker = (HorizontalPicker) view.findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker.setListener(this).init();

        preViewHolder.getAllpredictions().observe(getViewLifecycleOwner(), new Observer<List<Predictions>>() {
            @Override
            public void onChanged(List<Predictions> predictions) {

                for (int i = 0; i < predictions.size(); i++) {
                    favouritList.add(predictions.get(i).getId());
                }
            }
        });

        //getTodayDate();
        getAndSetPendingPrediction();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.game_array));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                switch (position) {
                    case 0:
                        gameCheck = "all";
                        if (dateCheck) {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + finalDate + "&sort=-id");
                        } else {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");
                        }

                        break;
                    case 1:
                        gameCheck = "football";
                        if (dateCheck) {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + finalDate + "&sort=-id");
                        } else {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");
                        }


                        break;
                    case 2:
                        gameCheck = "basketball";
                        if (dateCheck) {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + finalDate + "&sort=-id");
                        } else {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");
                        }


                        break;
                    case 3:
                        gameCheck = "hockey";
                        if (dateCheck) {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + finalDate + "&sort=-id");
                        } else {
                            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");
                        }

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (dateCheck) {
            txt_filter.setVisibility(View.VISIBLE);
        } else {
            txt_filter.setVisibility(View.GONE);
        }
        txt_filter.setOnClickListener(v -> {
            dateCheck = false;
            txt_filter.setVisibility(View.GONE);
            picker.clearFocus();

            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?sort=-id");

        });




        return view;
    }

    private void getBetReference() {
        RootRef.child("betReference").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("is_bet_enabled").exists()) {
                    isBetEnabled = dataSnapshot.child("is_bet_enabled").getValue().toString().equalsIgnoreCase("yes");
                    betLink = dataSnapshot.child("bet_link").getValue().toString();
                } else {
                    isBetEnabled = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        fetchRecords();
        /*try {
            add();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void getAndSetPendingPrediction() {
        todayPendingPredictionList = preViewHolder.getPendingPredictionsBaseONDate(finalTodayDate);
    }

    private void getAllPredictions(String URL) {
        list.clear();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject arrayJSONObject = array.getJSONObject(i);
                                int id = arrayJSONObject.getInt("id");
                                String status = arrayJSONObject.getString("status");
                                String sort = arrayJSONObject.getString("sort");
                                String owner = arrayJSONObject.getString("owner");
                                String created_on = arrayJSONObject.getString("created_on");
                                String modified_on = arrayJSONObject.getString("modified_on");
                                String league_name = arrayJSONObject.getString("league_name");
                                String match_id = arrayJSONObject.getString("match_id");
                                String home_team = arrayJSONObject.getString("home_team");
                                String away_team = arrayJSONObject.getString("away_team");
                                String odd_value = arrayJSONObject.getString("odd_value");
                                String team_home_score = arrayJSONObject.getString("team_home_score");
                                String team_away_score = arrayJSONObject.getString("team_away_score");
                                String match_minute = arrayJSONObject.getString("match_minute");
                                String coupon_name = arrayJSONObject.getString("coupon_name");
                                String game_prediction = arrayJSONObject.getString("game_prediction");
                                String match_status = arrayJSONObject.getString("match_status");
                                String match_date = arrayJSONObject.getString("match_date");
                                String match_time = arrayJSONObject.getString("match_time");
                                String match_timestamp = arrayJSONObject.getString("match_timestamp");
                                String sport_type = arrayJSONObject.getString("sport_type");

                                if (gameCheck.equals("all")) {
                                    if (favouritList.contains(id)) {
                                        idList.add(id);
                                        list.add(new AllPredictionsModel(id,
                                                status, sort, owner, created_on, modified_on, league_name,
                                                match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, true));
                                    } else {
                                        idList.add(id);
                                        list.add(new AllPredictionsModel(id,
                                                status, sort, owner, created_on, modified_on, league_name,
                                                match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, false));
                                    }
                                } else if (gameCheck.equals("football")) {
                                    if (sport_type.equals("football")) {
                                        idList.add(id);
                                        if (favouritList.contains(id)) {
                                            list.add(new AllPredictionsModel(id,
                                                    status, sort, owner, created_on, modified_on, league_name,
                                                    match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                    , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, true));
                                        } else {
                                            idList.add(id);
                                            list.add(new AllPredictionsModel(id,
                                                    status, sort, owner, created_on, modified_on, league_name,
                                                    match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                    , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, false));
                                        }
                                    }

                                } else if (gameCheck.equals("basketball")) {
                                    if (sport_type.equals("basketball")) {
                                        if (favouritList.contains(id)) {
                                            idList.add(id);
                                            list.add(new AllPredictionsModel(id,
                                                    status, sort, owner, created_on, modified_on, league_name,
                                                    match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                    , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, true));
                                        } else {
                                            idList.add(id);

                                            list.add(new AllPredictionsModel(id,
                                                    status, sort, owner, created_on, modified_on, league_name,
                                                    match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute
                                                    , coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type, false));
                                        }
                                    }
                                }


                            }

                            if (list != null && !list.isEmpty()) {
                                //Check if the subscription is valid/invalid
                                if (!isSubValid(getActivity()) && !dateCheck){
                                    String currentDate = getCurrentDate();
                                    List<AllPredictionsModel> listToBeRemoved = new ArrayList<>();
                                    for (int index=0; index<list.size(); index++){
                                        String matchDate = list.get(index).getMatch_date();
                                        if (index > 4 && matchDate.equals(currentDate)){
                                            listToBeRemoved.add(list.get(index));
                                        }
                                    }
                                    list.removeAll(listToBeRemoved);
                                }
                                //ENDED-> Check if the subscription is valid/invalid
                                txt_info.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                txt_info.setVisibility(View.VISIBLE);
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
                        progressBar.setVisibility(View.GONE);
//                        Log.d("Error.Response", error.getMessage());
//                        Toast.makeText(getContext(), "Error Responce " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("connection", "keep-alive");
                params.put("x-rapidapi-host", "daily-betting-tips.p.rapidapi.com");
                params.put("x-rapidapi-key", "1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f");
                return params;
            }
        };
        queue.add(postRequest);


    }

    @Override
    public void insertCall(int pos) {
        long ins = preViewHolder.insert(new Predictions(list.get(pos).getId(), list.get(pos).getStatus(), list.get(pos).getSort(),
                list.get(pos).getOwner(), list.get(pos).getCreated_on(), list.get(pos).getModified_on(), list.get(pos).getLeague_name(),
                list.get(pos).getMatch_id(), list.get(pos).getHome_team(), list.get(pos).getAway_team(), list.get(pos).getOdd_value(),
                list.get(pos).getTeam_home_score(), list.get(pos).getTeam_away_score(), list.get(pos).getMatch_minute(), list.get(pos).getCoupon_name(),
                list.get(pos).getGame_prediction(), list.get(pos).getMatch_date(), list.get(pos).getMatch_status(), list.get(pos).getMatch_time(),
                list.get(pos).getMatch_timestamp(), list.get(pos).getSport_type()));
    }

    @Override
    public void DeteteCall(int pos) {
        preViewHolder.deleteSpecificPerdictions(new Predictions(list.get(pos).getId(), list.get(pos).getStatus(), list.get(pos).getSort(),
                list.get(pos).getOwner(), list.get(pos).getCreated_on(), list.get(pos).getModified_on(), list.get(pos).getLeague_name(),
                list.get(pos).getMatch_id(), list.get(pos).getHome_team(), list.get(pos).getAway_team(), list.get(pos).getOdd_value(),
                list.get(pos).getTeam_home_score(), list.get(pos).getTeam_away_score(), list.get(pos).getMatch_minute(), list.get(pos).getCoupon_name(),
                list.get(pos).getGame_prediction(), list.get(pos).getMatch_date(), list.get(pos).getMatch_status(), list.get(pos).getMatch_time(),
                list.get(pos).getMatch_timestamp(), list.get(pos).getSport_type()));
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

        if (dateSelected != null) {
            dateCheck = true;
            txt_filter.setVisibility(View.VISIBLE);
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

            getAllPredictions("https://daily-betting-tips.p.rapidapi.com/daily-betting-tip-api/items/daily_betting_tips?q=" + finalDate + "&sort=-id");


        }
    }

    private void getTodayDate() {

        String input_date = "";
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();
        input_date = format1.format(date);

        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("yyyy");
        DateFormat format3 = new SimpleDateFormat("MM");
        DateFormat format4 = new SimpleDateFormat("dd");
        String finalYear = format2.format(dt1);
        String finalMonth = format3.format(dt1);
        String finalDay = format4.format(dt1);

        finalTodayDate = finalDay + "." + finalMonth + "." + finalYear;
    }

}