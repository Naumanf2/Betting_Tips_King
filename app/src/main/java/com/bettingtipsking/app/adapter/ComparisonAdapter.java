package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemComparisonBinding;
import com.bettingtipsking.app.model.FinalPredictionsModel;
import com.bettingtipsking.app.model.predictions.PredictionsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ComparisonAdapter extends RecyclerView.Adapter<ComparisonAdapter.ViewHolder> {
    Context context;
    List<FinalPredictionsModel> list;

    public ComparisonAdapter(Context context, List<FinalPredictionsModel> list) {
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

        holder.binding.textTeamHomeProgress.setText("Home ("+list.get(position).getComparisonHomeProgress()+")");
        holder.binding.textTeamAwayProgress.setText("Away ("+list.get(position).getComparisonAwayProgress()+")");

        holder.binding.textTeamHomeName.setText(list.get(position).getHomeTeamName());
        holder.binding.textTeamAwayName.setText(list.get(position).getAwayTeamName());
        holder.binding.textViewTitle.setText(list.get(position).getComparisonTitle());

        Glide.with(context).load(list.get(position).getHomeTeamLogo()).into(holder.binding.imageTeamHomeLogo);
        Glide.with(context).load(list.get(position).getAwayTeamLogo()).into(holder.binding.imageTeamAwayLogo);

        float scoreA= Float.parseFloat(list.get(position).getComparisonHomeProgress().replace("%", ""));
        float scoreB= Float.parseFloat(list.get(position).getComparisonAwayProgress().replace("%", ""));

        holder.binding.progressBarHome.setProgress((int) Math.min(scoreA, 100));
        holder.binding.progressBarAway.setProgress((int) Math.min(scoreB, 100));

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
