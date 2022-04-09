package com.bettingtipsking.app.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.bettingtipsking.app.R;


public class FavouriteLeaguesActivity extends AppCompatActivity {

   com.bettingtipsking.app.databinding.ActivityFavouriteLeaguesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_leagues);






    }
}