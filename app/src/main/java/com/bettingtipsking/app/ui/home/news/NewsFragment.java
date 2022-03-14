package com.bettingtipsking.app.ui.home.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.NewsModel;
import com.bettingtipsking.app.Model.VideosMainModel;
import com.bettingtipsking.app.Model.VideosModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.NewsAdapter;
import com.bettingtipsking.app.adapter.VideosMainAdapter;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;
    List<NewsModel> newsList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.newsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsList.add(new NewsModel("UEFA move champions League final from St Petersburg to paris"));
       newsAdapter = new NewsAdapter(getContext(), newsList);
       recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }
}