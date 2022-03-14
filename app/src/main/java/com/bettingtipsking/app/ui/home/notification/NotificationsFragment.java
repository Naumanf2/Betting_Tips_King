package com.bettingtipsking.app.ui.home.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Model.NotificationsModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.NotificationsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {



List<NotificationsModel> list = new ArrayList<>();
NotificationsAdapter adapter;
RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.rcl_notification);
        // Inflate the layout for this fragment
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));
list.add(new NotificationsModel("Your password  has been successfully changes thank you"));

        adapter = new NotificationsAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}