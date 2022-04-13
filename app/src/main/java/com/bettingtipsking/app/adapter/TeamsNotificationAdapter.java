package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.Helper.callback.MatchesNotificationCallBack;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemTeamsnotificationBinding;
import com.bettingtipsking.app.model.MatchesNotificationModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class TeamsNotificationAdapter extends RecyclerView.Adapter<TeamsNotificationAdapter.viewHolder> {

    Context context;
    List<MatchesNotificationModel> responseList;
    MatchesNotificationCallBack clickCallBack;


    public TeamsNotificationAdapter(Context context, List<MatchesNotificationModel> responseList, MatchesNotificationCallBack clickCallBack) {
        this.context = context;
        this.responseList = responseList;
        this.clickCallBack = clickCallBack;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_teamsnotification, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

         MatchesNotificationModel model = responseList.get(position);
         holder.binding.textTeamName.setText(model.getName());
        Glide.with(context).load(model.getLogo()).into(holder.binding.imageProfile);


        if (model.getStatus()==0)
            holder.binding.imageNotification.setImageDrawable(context.getDrawable(R.drawable.icon_notification_bell));
        else
            holder.binding.imageNotification.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_notifications_active_24));

        holder.binding.imageNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.notifiedMatches(holder.getAdapterPosition());
            }
        });

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
