package com.bettingtipsking.app.ui.home.home;

import static com.bettingtipsking.app.Helper.HelperClass.getCurrentDate;
import static com.bettingtipsking.app.Helper.HelperClass.isSubValid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bettingtipsking.app.adapter.HomePredictionsAdapter;
import com.bettingtipsking.app.Helper.PredictionsInterface;
import com.bettingtipsking.app.databinding.ActivityHomeBinding;
import com.bettingtipsking.app.databinding.FragmentHomeBinding;
import com.bettingtipsking.app.databinding.FragmentNewsBinding;
import com.bettingtipsking.app.model.HomelPredictionsModel;
import com.bettingtipsking.app.PreViewHolder;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.PendingPredictions;
import com.bettingtipsking.app.viewmodel.HomePredicationViewModel;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DatePickerListener {

    private FragmentHomeBinding binding;
    private HomePredicationViewModel viewModel;
    private List<HomelPredictionsModel> list;
    private HomePredictionsAdapter adapter;
    public boolean dateCheck = false;

    public String finalDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(HomePredicationViewModel.class);

        list = new ArrayList<>();

        adapter = new HomePredictionsAdapter(this, getContext(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // Listener
        binding.horizontalPicker.setListener(this).init();

        dateCheck = false;
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDateSelected(DateTime dateSelected) {

        if (dateSelected != null) {
            dateCheck = true;
           // txt_filter.setVisibility(View.VISIBLE);
            String year = String.valueOf(dateSelected.getYear());
            String month = String.valueOf(dateSelected.getMonthOfYear());
            String day = String.valueOf(dateSelected.getDayOfMonth());
            if (Integer.valueOf(month) < 10) {
                month = "0" + month;
            }
            if (Integer.valueOf(day) < 10) {
                day = "0" + day;
            }
            finalDate = day + "." + month + "." + year;

        }
    }


}