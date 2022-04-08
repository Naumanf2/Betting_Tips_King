package com.bettingtipsking.app.adapter;

import static com.bettingtipsking.app.Helper.HelperClass.H2H;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.activity.fixtures.FixtureDetailsActivity;
import com.bettingtipsking.app.activity.fixtures.model.FinalMatchDetailsModel;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemMatchesBinding;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.bettingtipsking.app.ui.home.matches.fragment.LiveFixturesFragment;
import com.bumptech.glide.Glide;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {

    Context context;
    List<FinalMatchesModel> list;
    ItemClickListener listener;
    boolean h2h;

    public MatchesAdapter(Context context, List<FinalMatchesModel> list, ItemClickListener listener, boolean h2h) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.h2h = h2h;
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

        String date = model.getFixture().getDate();

        Glide.with(context).load(model.getTeams().getHome().getLogo()).into(holder.binding.imageTeamHomeLogo);
        Glide.with(context).load(model.getTeams().getAway().getLogo()).into(holder.binding.imageTeamAwayLogo);

        holder.binding.textTeamHomeName.setText(model.getTeams().getHome().getName());
        holder.binding.textTeamAwayName.setText(model.getTeams().getAway().getName());
        holder.binding.textMatchStatus.setText(model.getFixture().getStatus().getLong());
        holder.binding.textDate.setText(date.substring(0, 10));

        if (!String.valueOf(model.getGoals().getHome()).equals("null")) {
            holder.binding.textTeamHomeScore.setText("" + model.getGoals().getHome());
            holder.binding.textTeamAwayScore.setText("" + model.getGoals().getAway());
        } else {
            holder.binding.textTeamHomeScore.setText("-");
            holder.binding.textTeamAwayScore.setText("-");
        }

        String shortStatus = list.get(position).getFixture().getStatus().getShort();

        if (shortStatus.equalsIgnoreCase("live")) {
            holder.binding.spinKitWave.setVisibility(View.VISIBLE);
        } else {
            holder.binding.spinKitWave.setVisibility(View.GONE);
        }

        if (h2h)
            holder.binding.imageExpand.setVisibility(View.GONE);
        else
            holder.binding.imageExpand.setVisibility(View.VISIBLE);


        holder.itemView.setOnClickListener(v -> {
           listener.onItemClicked(position,list.get(holder.getAdapterPosition()));
        });

        holder.binding.imageExpand.setOnClickListener(v -> {
            listener.onH2HIconClick(list.get(holder.getAdapterPosition()).getTeams().getHome().getId(), list.get(holder.getAdapterPosition()).getTeams().getAway().getId());
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filtredFist(List<FinalMatchesModel> filterMatchesModelList) {
        list = filterMatchesModelList;
        notifyDataSetChanged();
    }
}

class MatchesViewHolder extends RecyclerView.ViewHolder {
    ItemMatchesBinding binding;

    public MatchesViewHolder(ItemMatchesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
