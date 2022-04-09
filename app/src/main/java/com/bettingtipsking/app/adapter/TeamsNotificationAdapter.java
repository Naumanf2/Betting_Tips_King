package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemTeamsnotificationBinding;
import com.bettingtipsking.app.model.teamsInfo.Response;
import com.bumptech.glide.Glide;

import java.util.List;

public class TeamsNotificationAdapter extends RecyclerView.Adapter<TeamsNotificationAdapter.viewHolder> {

    Context context;
    List<Response> responseList;

    public TeamsNotificationAdapter(Context context, List<Response> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamsNotificationAdapter.viewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_teamsnotification, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Response model= responseList.get(holder.getAdapterPosition());
        holder.binding.textTeamName.setText(model.getTeam().getName());

        Glide.with(context).load(model.getTeam().getLogo()).into(holder.binding.imageProfile);

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemTeamsnotificationBinding binding;
        public viewHolder(ItemTeamsnotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
