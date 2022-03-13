package com.bettingtipsking.app.activity.fixtures.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    Context context;
    List<TeamWithEventsModel> list;

    public EventsAdapter(Context context, List<TeamWithEventsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        holder.tvTeamTitle.setText(list.get(position).getName());
        Glide.with(context)
                .load(list.get(position).getLogo())
                .into(holder.ivTeamFlag);

        Log.e("event list size: ", String.valueOf(list.get(position).getEventsList().size()));
        EventAdapter adapter = new EventAdapter(context, list.get(position).getEventsList());
        holder.rvEvents.setLayoutManager(new LinearLayoutManager(context));
        holder.rvEvents.setAdapter(adapter);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamTitle;
        ImageView ivTeamFlag;
        RecyclerView rvEvents;
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamTitle = itemView.findViewById(R.id.team_title_tv);
            ivTeamFlag = itemView.findViewById(R.id.team_flag_iv);
            rvEvents = itemView.findViewById(R.id.events_rv);
        }
    }
}