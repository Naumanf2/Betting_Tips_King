package com.bettingtipsking.app.adapter;

import static com.bettingtipsking.app.Helper.HelperClass.H2H;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.activity.fixtures.model.FinalMatchDetailsModel;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemMatchesBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {

    Context context;
    List<FinalMatchesModel> list;
    ItemClickListener listener;
    int parentPosition;

    public MatchesAdapter(Context context, List<FinalMatchesModel> list, ItemClickListener listener, int parentPosition) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.parentPosition = parentPosition;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MatchesViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_matches, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        FinalMatchesModel model = list.get(position);

        Glide.with(context).load(model.getTeams().getHome().getLogo()).into(holder.binding.imageTeamHomeLogo);
        Glide.with(context).load(model.getTeams().getAway().getLogo()).into(holder.binding.imageTeamAwayLogo);

        holder.binding.textTeamHomeName.setText(model.getTeams().getHome().getName());
        holder.binding.textTeamAwayName.setText(model.getTeams().getAway().getName());

        if (!String.valueOf(model.getGoals().getHome()).equals("null")){
            holder.binding.textTeamHomeScore.setText(""+model.getGoals().getHome());
            holder.binding.textTeamAwayScore.setText(""+model.getGoals().getAway());
        }else {
            holder.binding.textTeamHomeScore.setText("-");
            holder.binding.textTeamAwayScore.setText("-");
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClicked(String.valueOf(parentPosition), model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MatchesViewHolder extends RecyclerView.ViewHolder {
    ItemMatchesBinding binding;

    public MatchesViewHolder(ItemMatchesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
