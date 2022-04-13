package com.bettingtipsking.app.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bettingtipsking.app.model.HomelPredictionsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePredicationRepository {
    Application application;
    RequestQueue queue;
    List<HomelPredictionsModel> list;
    MutableLiveData<List<HomelPredictionsModel>> listMutableLiveData;

    public HomePredicationRepository(Application application) {
        this.application = application;
        queue = Volley.newRequestQueue(application);
        list = new ArrayList<>();
        listMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<HomelPredictionsModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void getAllPredictions(String URL) {
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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

                                if (sport_type.equalsIgnoreCase("football")) {
                                    list.add(new HomelPredictionsModel(id, status, sort, owner, created_on, modified_on, league_name, match_id, home_team, away_team, odd_value, team_home_score, team_away_score, match_minute, coupon_name, game_prediction, match_date, match_status, match_time, match_timestamp, sport_type));
                                }

                            }
                            listMutableLiveData.postValue(list);

                        } catch (Exception e) {
                            e.printStackTrace();
                            //todo Exception handling
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //todo error Response
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
}


