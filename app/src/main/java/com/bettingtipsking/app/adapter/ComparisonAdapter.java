package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemComparisonBinding;
import com.bettingtipsking.app.model.predictions.PredictionsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ComparisonAdapter extends RecyclerView.Adapter<ComparisonAdapter.ViewHolder> {
    Context context;
    List<PredictionsModel> list;

    public ComparisonAdapter(Context context, List<PredictionsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComparisonAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comparison, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.binding.textTeamHomeProgress.setText("Home "+list.get(position).getResponse().get(position).getComparison().getForm().getHome());
        holder.binding.textTeamAwayProgress.setText("Away "+list.get(position).getResponse().get(position).getComparison().getForm().getAway());
        holder.binding.textTeamHomeName.setText(list.get(position).getResponse().get(position).getTeams().getHome().getName());
        holder.binding.textTeamHomeName.setText(list.get(position).getResponse().get(position).getTeams().getAway().getName());

        Glide.with(context).load(list.get(position).getResponse().get(position).getTeams().getHome().getLogo()).into(holder.binding.imageTeamHomeLogo);
        Glide.with(context).load(list.get(position).getResponse().get(position).getTeams().getAway().getLogo()).into(holder.binding.imageTeamAwayLogo);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemComparisonBinding binding;
        public ViewHolder( ItemComparisonBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
