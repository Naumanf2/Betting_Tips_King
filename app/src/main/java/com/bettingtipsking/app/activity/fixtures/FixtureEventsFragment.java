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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bettingtipsking.app.Helper.MySingleton;
import com.bettingtipsking.app.activity.fixtures.adapters.EventsAdapter;
import com.bettingtipsking.app.activity.fixtures.model.EventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bettingtipsking.app.databinding.FragmentFixtureEventsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureEventsFragment extends Fragment {

    FixtureDetailsActivity parentContext;
    FragmentFixtureEventsBinding binding;
    EventsAdapter adapter;
    List<TeamWithEventsModel> teamsWithEventsList = new ArrayList<>();
    Map<Integer, Integer> teamsWithEventsMap = new  HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFixtureEventsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    public void init(){
        adapter = new EventsAdapter(getContext(), teamsWithEventsList);
        binding.eventsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventsRv.setAdapter(adapter);
        getEvents();
    }

    private void getEvents() {
        String url = "https://api-football-v1.p.rapidapi.com/v3/fixtures?id="+parentContext.matchDetails.getFixtureId();
        Log.e("URL::", String.valueOf(parentContext.matchDetails.getFixtureId()));
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.txtInfo.setVisibility(View.GONE);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        binding.progressBar.setVisibility(View.GONE);
                        try {
                            teamsWithEventsList.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            JSONArray events = array.getJSONObject(0).getJSONArray("events");

                            Log.e("response::", response);
                            for (int i = 0; i < events.length(); i++) {
                                JSONObject indexedJsonObject = events.getJSONObject(i);

                                String type = indexedJsonObject.getString("type");
                                String timeElapsed = indexedJsonObject.getJSONObject("time").getString("elapsed");
                                String playerId = indexedJsonObject.getJSONObject("player").getString("id");
                                String playerName = indexedJsonObject.getJSONObject("player").getString("name");
                                String assistId = indexedJsonObject.getJSONObject("assist").getString("id");
                                String assistName = indexedJsonObject.getJSONObject("assist").getString("name");
                                String details = indexedJsonObject.getString("detail");

                                EventsModel eventsModel = new EventsModel(type, timeElapsed, playerId, playerName, assistId, assistName, details);

                                String id = indexedJsonObject.getJSONObject("team").getString("id");
                                String name = indexedJsonObject.getJSONObject("team").getString("name");
                                String logo = indexedJsonObject.getJSONObject("team").getString("logo");
                                TeamWithEventsModel teamWithEventsModel = new TeamWithEventsModel(id, name, logo, new ArrayList<>());

                                if (!teamsWithEventsMap.containsKey(Integer.parseInt(id))){
                                    teamsWithEventsList.add(teamWithEventsModel);
                                    teamsWithEventsMap.put(Integer.parseInt(id), teamsWithEventsList.indexOf(teamWithEventsModel));
                                }
                                teamsWithEventsList.get(teamsWithEventsMap.get(Integer.parseInt(id))).getEventsList().add(eventsModel);

                            }

                            if (teamsWithEventsList != null && !teamsWithEventsList.isEmpty()) {
                                binding.txtInfo.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.txtInfo.setVisibility(View.VISIBLE);
                            }


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