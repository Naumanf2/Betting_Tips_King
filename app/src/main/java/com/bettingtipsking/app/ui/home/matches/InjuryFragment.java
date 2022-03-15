package com.bettingtipsking.app.ui.home.matches;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.InjuryModel;
import com.bettingtipsking.app.Model.NewsModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.InjuryAdapter;
import com.bettingtipsking.app.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;


public class InjuryFragment extends Fragment {

    InjuryAdapter injuryAdapter;
    RecyclerView recyclerView;
    List<InjuryModel> injuryList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_injury, container, false);

        recyclerView = view.findViewById(R.id.injuryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryList.add(new InjuryModel("Tom Brady"));
        injuryAdapter = new InjuryAdapter(getContext(), injuryList);
        recyclerView.setAdapter(injuryAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}