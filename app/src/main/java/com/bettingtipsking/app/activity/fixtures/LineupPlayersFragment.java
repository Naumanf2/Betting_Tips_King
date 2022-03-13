package com.bettingtipsking.app.activity.fixtures;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.adapters.EventsAdapter;
import com.bettingtipsking.app.activity.fixtures.adapters.LineupPlayersAdapter;
import com.bettingtipsking.app.activity.fixtures.model.EventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bettingtipsking.app.databinding.FragmentFixtureEventsBinding;
import com.bettingtipsking.app.databinding.FragmentLinupPlayersBinding;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineupPlayersFragment extends Fragment {

    FixtureDetailsActivity parentContext;
    FragmentLinupPlayersBinding binding;
    List<TeamWithPlayers> teamsWithPlayersList = new ArrayList<>();
    Map<Integer, String> playersMap = new  HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLinupPlayersBinding.inflate(inflater, container, false);
        getPlayers();

        return binding.getRoot();
    }

    public void init(){
        LineupPlayersAdapter adapterKeeper = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getGoalKeeper());
        binding.keeper1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.keeper1Rv.setAdapter(adapterKeeper);
        LineupPlayersAdapter adapterDefender = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getDefender());
        binding.defender1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.defender1Rv.setAdapter(adapterDefender);
        LineupPlayersAdapter adapterMidfielder = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getMidfielder());
        binding.midfielder1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.midfielder1Rv.setAdapter(adapterMidfielder);
        LineupPlayersAdapter adapterForward = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(0).getForward());
        binding.forward1Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.forward1Rv.setAdapter(adapterForward);

        LineupPlayersAdapter adapterKeeper2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getGoalKeeper());
        binding.keeper2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.keeper2Rv.setAdapter(adapterKeeper2);
        LineupPlayersAdapter adapterDefender2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getDefender());
        binding.defender2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.defender2Rv.setAdapter(adapterDefender2);
        LineupPlayersAdapter adapterMidfielder2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getMidfielder());
        binding.midfielder2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.midfielder2Rv.setAdapter(adapterMidfielder2);
        LineupPlayersAdapter adapterForward2 = new LineupPlayersAdapter(getContext(), teamsWithPlayersList.get(1).getForward());
        binding.forward2Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.forward2Rv.setAdapter(adapterForward2);

        binding.teamTitleTv.setText(teamsWithPlayersList.get(0).getName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(0).getLogo())
                .into(binding.teamFlagIv);
        binding.coachNameTv.setText("Coach: "+teamsWithPlayersList.get(0).getCoachName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(0).getCoachPhoto())
                .into(binding.coachIv);

        binding.team2TitleTv.setText(teamsWithPlayersList.get(1).getName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(1).getLogo())
                .into(binding.team2FlagIv);
        binding.coach2NameTv.setText("Coach: "+teamsWithPlayersList.get(1).getCoachName());
        Glide.with(getActivity())
                .load(teamsWithPlayersList.get(1).getCoachPhoto())
                .into(binding.coach2Iv);



    }

    private void getPlayers() {
        String url = "https://api-football-v1.p.rapidapi.com/v3/fixtures?id="+parentContext.matchDetails.getFixtureId();

        Log.e("getPlayers::", String.valueOf(parentContext.matchDetails.getFixtureId()));
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            teamsWithPlayersList.clear();
                            playersMap.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            JSONArray lineups = array.getJSONObject(0).getJSONArray("lineups");
                            JSONArray players = array.getJSONObject(0).getJSONArray("players");
                            JSONArray teamAPlayers = new JSONArray();
                            JSONArray teamBPlayers = new JSONArray();
                            if (players.length() > 0){
                                teamAPlayers = players.getJSONObject(0).getJSONArray("players");
                                teamBPlayers = players.getJSONObject(1).getJSONArray("players");
                            }
//                            JSONArray teamAPlayers = array.getJSONObject(0).getJSONArray("players").getJSONObject(0).getJSONArray("players");
//                            JSONArray teamBPlayers = array.getJSONObject(0).getJSONArray("players").getJSONObject(1).getJSONArray("players");

                            for (int indA = 0; indA < teamAPlayers.length(); indA++){
                                JSONObject indexedJsonObject = teamAPlayers.getJSONObject(indA);
                                int  playerId = indexedJsonObject.getJSONObject("player").getInt("id");
                                String  playerPhoto = indexedJsonObject.getJSONObject("player").getString("photo");
                                playersMap.put(playerId, playerPhoto);
                            }
                            for (int indB = 0; indB < teamBPlayers.length(); indB++){
                                JSONObject indexedJsonObject = teamBPlayers.getJSONObject(indB);
                                int  playerId = indexedJsonObject.getJSONObject("player").getInt("id");
                                String  playerPhoto = indexedJsonObject.getJSONObject("player").getString("photo");
                                playersMap.put(playerId, playerPhoto);
                            }
                            Log.e("response::", response);
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
                                for (int index = 0; index < startXI.length(); index++){
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

                                    playerPosTest = playerPosTest+"\n"+playerPos;
                                    Log.e("player pos::", playerPos);
                                    if (playerPos.equalsIgnoreCase("G")){
                                        goalKeeper.add(model);
                                    }else if (playerPos.equalsIgnoreCase("D")){
                                        defender.add(model);
                                    }else if (playerPos.equalsIgnoreCase("M")){
                                        midfielder.add(model);
                                    }else{
                                        forward.add(model);
                                    }
                                   /* switch (playerPos) {
                                        case "G":
                                            goalKeeper.add(model);
                                            break;
                                        case "D":
                                            defender.add(model);
                                            break;
                                        case "M":
                                            midfielder.add(model);
                                            break;
                                        default:
                                            forward.add(model);
                                            break;
                                    }*/
                                }


                                Log.e("playerPosTest::", playerPosTest);
                                Log.e("goalKeeper::", String.valueOf(goalKeeper.size()));
                                Log.e("defender::", String.valueOf(defender.size()));
                                Log.e("midfielder::", String.valueOf(midfielder.size()));
                                Log.e("forward::", String.valueOf(forward.size()));

                                TeamWithPlayers teamWithPlayers = new TeamWithPlayers(id, name, logo, coachName, coachPhoto, formation, goalKeeper, defender, midfielder, forward);
                                teamsWithPlayersList.add(teamWithPlayers);
                            }

                            init();
                            Log.e("teamsWithPlayersList::", String.valueOf(teamsWithPlayersList.size()));
                            Log.e("teams a::", String.valueOf(teamsWithPlayersList.get(0).getForward().size()));
                            Log.e("teams a::", String.valueOf(teamsWithPlayersList.get(0).getDefender().size()));
                            Log.e("teams b::", String.valueOf(teamsWithPlayersList.get(1).getForward().size()));
                            Log.e("teams b::", String.valueOf(teamsWithPlayersList.get(1).getDefender().size()));

                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Exception! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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