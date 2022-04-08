package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemSquadBinding;
import com.bettingtipsking.app.model.squad.Player;
import com.bettingtipsking.app.model.squad.Response;
import com.bumptech.glide.Glide;

import java.util.List;

public class SquadAdapter extends RecyclerView.Adapter<SquadAdapter.ViewHolder> {
    Context context;
    List<Player> list;

    public SquadAdapter(Context context, List<Player> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SquadAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_squad, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.textName.setText(list.get(position).getName());
        holder.binding.textPosition.setText(list.get(position).getPosition());
        Glide.with(context).load(list.get(position).getPhoto()).into(holder.binding.imagePlayerProfile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSquadBinding binding;

        public ViewHolder(ItemSquadBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
