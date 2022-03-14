package com.bettingtipsking.app.ui.video;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.VideosMainModel;
import com.bettingtipsking.app.Model.VideosModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.VideosMainAdapter;

import java.util.ArrayList;
import java.util.List;


public class VideosFragment extends Fragment {
    VideosMainAdapter videosMainAdapter;
    RecyclerView recyclerView;
    List<VideosMainModel> videosMainList = new ArrayList<>();
    List<VideosModel> videosList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        recyclerView = view.findViewById(R.id.videosMainRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        videosList.add(new VideosModel("Ancelotti: Real Madrid Benzema and Modric get better every day"));
        videosList.add(new VideosModel("Ancelotti: Real Madrid Benzema and Modric get better every day"));
        videosList.add(new VideosModel("Ancelotti: Real Madrid Benzema and Modric get better every day"));
        videosList.add(new VideosModel("Ancelotti: Real Madrid Benzema and Modric get better every day"));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainList.add(new VideosMainModel(videosList));
        videosMainAdapter = new VideosMainAdapter(getContext(), videosMainList);
        recyclerView.setAdapter(videosMainAdapter);
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }
}