package com.bettingtipsking.app.activity.Favourite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bettingtipsking.app.activity.Favourite.Adapter.FavouriteAdapter;
import com.bettingtipsking.app.Helper.PredictionsInterface;
import com.bettingtipsking.app.PreViewHolder;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.Predictions;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements PredictionsInterface {

    RecyclerView recyclerView;
    TextView txt_info;
    FavouriteAdapter adapter;
    List<Predictions> list = new ArrayList<>();
    PreViewHolder preViewHolder;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        preViewHolder = new ViewModelProvider(this).get(PreViewHolder.class);


        list = new ArrayList<>();
        txt_info = view.findViewById(R.id.txt_info);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new FavouriteAdapter(getContext(), list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


      preViewHolder.getAllpredictions().observe(getViewLifecycleOwner(), new Observer<List<Predictions>>() {
            @Override
            public void onChanged(List<Predictions> predictions) {
                list = predictions;


                if(list !=null && ! list.isEmpty()){
                    txt_info.setVisibility(View.GONE);
                    adapter.setData(list);
                    // do your code here
                }else {
                    txt_info.setVisibility(View.VISIBLE);
                }

            }
        });
        return view;
    }


    @Override
    public void insertCall(int id) {
    }

    @Override
    public void DeteteCall(int id) {
        preViewHolder.deleteSpecificPerdictions(list.get(id));
    }
}