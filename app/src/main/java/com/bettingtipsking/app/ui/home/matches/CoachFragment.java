package com.bettingtipsking.app.ui.home.matches;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.MatchesModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.CoachesDetailAdapter;

import java.util.ArrayList;
import java.util.List;


public class CoachFragment extends Fragment {
    List<MatchesModel> list=new ArrayList<>();
    RecyclerView recyclerView;
    CoachesDetailAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coach, container, false);

        recyclerView=view.findViewById(R.id.rcl_coach);


        list.add(new MatchesModel("Youbanto"));
        list.add(new MatchesModel("Madrid"));
        list.add(new MatchesModel("Barcelona"));
        list.add(new MatchesModel("Youbanto"));
        list.add(new MatchesModel("Youbanto"));
        adapter=new CoachesDetailAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);




        return view;
    }
}