package com.bettingtipsking.app.ui.follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.MainActivity;
import com.bettingtipsking.app.adapter.EventsAdapter;
import com.bettingtipsking.app.adapter.TeamsNotificationAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityFollowTeamBinding;
import com.bettingtipsking.app.model.events.EventsModel;
import com.bettingtipsking.app.model.teamsInfo.Response;
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.TeamsInfoViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.TeamsInfoViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FollowTeamActivity extends AppCompatActivity {

    ActivityFollowTeamBinding binding;

    TeamsInfoViewModel viewModel;
    TeamsNotificationAdapter adapter;
    List<TeamsInfoModel> list;
    List<Response> responseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow_team);
        //setContentView(R.layout.activity_follow_team);

        viewModel = new ViewModelProvider(this, new TeamsInfoViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(TeamsInfoViewModel.class);
        viewModel.getLeague(5,2022);

        list = new ArrayList<>();
        responseList = new ArrayList<>();

        viewModel.getData().observe(this, teamsInfoModel ->{
            responseList= teamsInfoModel.getResponse();

            adapter=new TeamsNotificationAdapter(FollowTeamActivity.this,responseList);
            binding.recyclerViewFollowTeams.setLayoutManager(new LinearLayoutManager(FollowTeamActivity.this,LinearLayoutManager.VERTICAL,false));
            binding.recyclerViewFollowTeams.setAdapter(adapter);



        } );
    }
}