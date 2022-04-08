package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemEventsBinding;
import com.bettingtipsking.app.model.events.EventsModel;
import com.bettingtipsking.app.model.events.Response;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Context context;
    List<Response> responseList;

    public EventsAdapter(Context context, List<Response> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventsAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_events, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.textTeamName.setText(responseList.get(position).getTeam().getName());
        if (responseList.get(position).getType().equalsIgnoreCase("Goal")) {
            holder.binding.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.goal));
        } else if (responseList.get(position).getType().equalsIgnoreCase("card")) {
            holder.binding.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.card));
        }

        holder.binding.textPlayerName.setText(responseList.get(position).getPlayer().getName());
        holder.binding.textTimeElapsed.setText(""+responseList.get(position).getTime().getElapsed());
        holder.binding.textEventDetail.setText(responseList.get(position).getDetail());
        Glide.with(context).load(responseList.get(position).getTeam().getLogo()).into(holder.binding.imageTeamLogo);
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemEventsBinding binding;

        public ViewHolder(ItemEventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
