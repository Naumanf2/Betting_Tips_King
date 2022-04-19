package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemStatisticsHomeBinding;
import com.bettingtipsking.app.model.StatisticsModel;
import com.bettingtipsking.app.model.statistics.Response;
import com.bumptech.glide.Glide;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    Context context;
    List<StatisticsModel> list;

    public StatisticsAdapter(Context context, List<StatisticsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatisticsAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_statistics_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getTeamLogo()).into(holder.binding.imageTeamLogo);

        holder.binding.textStatisticsType.setText(list.get(position).getStatisticsType()+" "+list.get(position).getStatisticsVale());
        holder.binding.textStatisticsValue.setText(list.get(position).getStatisticsVale());
        holder.binding.textTeamName.setText(list.get(position).getTeamName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemStatisticsHomeBinding binding;

        public ViewHolder(ItemStatisticsHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
