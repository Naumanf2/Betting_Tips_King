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

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Context context;
    List<Response> responseList;
    public EventsAdapter(Context context,  List<Response> responseList) {
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
