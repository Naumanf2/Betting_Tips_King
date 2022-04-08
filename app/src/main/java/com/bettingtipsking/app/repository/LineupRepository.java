package com.bettingtipsking.app.repository;

import static com.bettingtipsking.app.Helper.HelperClass.HOST;
import static com.bettingtipsking.app.Helper.HelperClass.KEY;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineupRepository {
    Application application;
    Map<Integer, String> playersMap;
    List<TeamWithPlayers> list;
    MutableLiveData<List<TeamWithPlayers>> mutableLiveData;


    public LineupRepository(Application application) {
        this.application = application;
        playersMap = new HashMap<>();
        list = new ArrayList<>();
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<TeamWithPlayers>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getLineup(String url) {
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    list.clear();
                    playersMap.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("response");
                    JSONArray lineups = array.getJSONObject(0).getJSONArray("lineups");
                    JSONArray players = array.getJSONObject(0).getJSONArray("players");
                    JSONArray teamAPlayers = new JSONArray();
                    JSONArray teamBPlayers = new JSONArray();
                    if (players.length() > 0) {
                        teamAPlayers = players.getJSONObject(0).getJSONArray("players");
                        teamBPlayers = players.getJSONObject(1).getJSONArray("players");
                    }
                    for (int indA = 0; indA < teamAPlayers.length(); indA++) {
                        JSONObject indexedJsonObject = teamAPlayers.getJSONObject(indA);
                        int playerId = indexedJsonObject.getJSONObject("player").getInt("id");
                        String playerPhoto = indexedJsonObject.getJSONObject("player").getString("photo");
                        playersMap.put(playerId, playerPhoto);
                    }
                    for (int indB = 0; indB < teamBPlayers.length(); indB++) {
                        JSONObject indexedJsonObject = teamBPlayers.getJSONObject(indB);
                        int playerId = indexedJsonObject.getJSONObject("player").getInt("id");
                        String playerPhoto = indexedJsonObject.getJSONObject("player").getString("photo");

                       System.out.println("player photo is "+playerPhoto);
                        playersMap.put(playerId, playerPhoto);
                    }
                    for (int i = 0; i < lineups.length(); i++) {
                        JSONObject indexedJsonObject = lineups.getJSONObject(i);
                        JSONObject team = indexedJsonObject.getJSONObject("team");
                        String id = team.getString("id");
                        String name = team.getString("name");
                        String logo = team.getString("logo");

                        JSONObject coach = indexedJsonObject.getJSONObject("coach");
                        String coachName = coach.getString("name");
                        String coachPhoto = coach.getString("photo");
                        String formation = indexedJsonObject.getString("formation");

                        List<TeamWithPlayers> goalKeeper = new ArrayList<>();
                        List<TeamWithPlayers> defender = new ArrayList<>();
                        List<TeamWithPlayers> midfielder = new ArrayList<>();
                        List<TeamWithPlayers> forward = new ArrayList<>();
                        String playerPosTest = "";

                        JSONArray startXI = indexedJsonObject.getJSONArray("startXI");
                        for (int index = 0; index < startXI.length(); index++) {
                            JSONObject startXIObject = startXI.getJSONObject(index);

                            JSONObject player = startXIObject.getJSONObject("player");
                            String playerId = player.getString("id");
                            String playerName = player.getString("name");
                            String playerNumber = player.getString("number");
                            String playerPos = player.getString("pos");
                            String playerGrid = player.getString("grid");
                            String playerPhoto = "";
                            if (!playerId.equals("null"))
                                playerPhoto = playersMap.get(Integer.parseInt(playerId));

                            TeamWithPlayers model = new TeamWithPlayers(playerId, playerName, playerNumber, playerPos, playerGrid, playerPhoto);

                            playerPosTest = playerPosTest + "\n" + playerPos;
                            if (playerPos.equalsIgnoreCase("G")) {
                                goalKeeper.add(model);
                            } else if (playerPos.equalsIgnoreCase("D")) {
                                defender.add(model);
                            } else if (playerPos.equalsIgnoreCase("M")) {
                                midfielder.add(model);
                            } else {
                                forward.add(model);
                            }

                        }

                        TeamWithPlayers teamWithPlayers = new TeamWithPlayers(id, name, logo, coachName, coachPhoto, formation, goalKeeper, defender, midfielder, forward);
                        list.add(teamWithPlayers);
                        mutableLiveData.postValue(list);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    //todo exception
                    mutableLiveData.postValue(null);
                }


            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //todo something is not ok
                        mutableLiveData.postValue(null);
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
        MySingleton.getmInstance(application).addToRequestQueue(postRequest);
    }

}
