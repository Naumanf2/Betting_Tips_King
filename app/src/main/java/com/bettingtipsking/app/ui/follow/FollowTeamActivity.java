package com.bettingtipsking.app.ui.follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.bettingtipsking.app.Helper.callback.MatchesNotificationCallBack;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.TeamsNotificationAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.ActivityFollowTeamBinding;
import com.bettingtipsking.app.model.MatchesNotificationModel;
import com.bettingtipsking.app.model.teamsInfo.Response;
import com.bettingtipsking.app.model.teamsInfo.TeamsInfoModel;
import com.bettingtipsking.app.room.entity.MatchesNotificationEntity;
import com.bettingtipsking.app.viewmodel.TeamsInfoViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.TeamsInfoViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FollowTeamActivity extends AppCompatActivity implements MatchesNotificationCallBack {

    ActivityFollowTeamBinding binding;

    TeamsInfoViewModel viewModel;
    TeamsNotificationAdapter adapter;
    List<TeamsInfoModel> list;
    List<Response> responseList;
    List<Integer> listIDs;
    List<MatchesNotificationModel> matchesNotificationModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow_team);
        Intent mIntent = getIntent();
        int leagueID = mIntent.getIntExtra("LeagueID", 0);

        viewModel = new ViewModelProvider(this, new TeamsInfoViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class),getApplication())).get(TeamsInfoViewModel.class);
        viewModel.getMatches(leagueID, 2022);

        list = new ArrayList<>();
        responseList = new ArrayList<>();
        listIDs = new ArrayList<>();
        matchesNotificationModels = new ArrayList<>();

        adapter = new TeamsNotificationAdapter(FollowTeamActivity.this, matchesNotificationModels,this);
        binding.recyclerViewFollowTeams.setLayoutManager(new LinearLayoutManager(FollowTeamActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewFollowTeams.setAdapter(adapter);

       /* viewModel.getData().observe(this, teamsInfoModel -> {
            responseList = teamsInfoModel.getResponse();
            adapter = new TeamsNotificationAdapter(FollowTeamActivity.this, responseList);
            binding.recyclerViewFollowTeams.setLayoutManager(new LinearLayoutManager(FollowTeamActivity.this, LinearLayoutManager.VERTICAL, false));
            binding.recyclerViewFollowTeams.setAdapter(adapter);

        });*/

        viewModel.getAllNotifiedMatches().observe(this,matchesNotificationEntities -> {

            for (MatchesNotificationEntity model : matchesNotificationEntities) {
                listIDs.add(model.getId());
            }
        });

        viewModel.getData().observe(this,teamsInfoModel -> {
            for (int i = 0; i < teamsInfoModel.getResponse().size(); i++) {
                matchesNotificationModels.add(new MatchesNotificationModel(teamsInfoModel.getResponse().get(i).getTeam().getId(), teamsInfoModel.getResponse().get(i).getTeam().getName(), teamsInfoModel.getResponse().get(i).getTeam().getLogo(), 1));
            }
            for (int i = 0; i < matchesNotificationModels.size(); i++) {
                if (listIDs.contains(matchesNotificationModels.get(i).getId()))
                    matchesNotificationModels.get(i).setStatus(0);
            }
            adapter.notifyDataSetChanged();

        });
    }

    @Override
    public void notifiedMatches(int position) {
        if (matchesNotificationModels.get(position).getStatus() == 0) {
            matchesNotificationModels.get(position).setStatus(1);
            viewModel.deleteNotifiedMatches(new MatchesNotificationEntity(matchesNotificationModels.get(position).getId(),matchesNotificationModels.get(position).getName(),matchesNotificationModels.get(position).getLogo()));
            adapter.notifyItemChanged(position);
        } else {
            matchesNotificationModels.get(position).setStatus(0);
            viewModel.insertNotifiedMatches(new MatchesNotificationEntity(matchesNotificationModels.get(position).getId(),matchesNotificationModels.get(position).getName(),matchesNotificationModels.get(position).getLogo()));
            adapter.notifyItemChanged(position);
        }
    }
}
