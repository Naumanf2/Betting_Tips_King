package com.bettingtipsking.app.ui.home.matches;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.MainMatchesModel;
import com.bettingtipsking.app.Model.MatchesModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.MainMatchesAdapter;

import java.util.ArrayList;
import java.util.List;


public class MatchesFragment extends Fragment {

MainMatchesAdapter adapter;
List<MainMatchesModel> modelList=new ArrayList<>();
List<MatchesModel> subModel=new ArrayList<>();
RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_matches, container, false);

recyclerView=view.findViewById(R.id.matches_rcl_main);

subModel.add(new MatchesModel("FC Barcelona"));
subModel.add(new MatchesModel("Real Madrid"));
subModel.add(new MatchesModel("FC Barcelona"));
subModel.add(new MatchesModel("FC Barcelona"));
subModel.add(new MatchesModel("FC Barcelona"));
subModel.add(new MatchesModel("FC Barcelona"));
subModel.add(new MatchesModel("FC Barcelona"));

modelList.add(new MainMatchesModel("England",subModel));
modelList.add(new MainMatchesModel("Pakistan",subModel));
modelList.add(new MainMatchesModel("UK",subModel));
modelList.add(new MainMatchesModel("Dubai",subModel));
modelList.add(new MainMatchesModel("Canada",subModel));
modelList.add(new MainMatchesModel("Australia",subModel));


adapter=new MainMatchesAdapter(getContext(),modelList);
recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
recyclerView.setAdapter(adapter);



        return view;
    }
}