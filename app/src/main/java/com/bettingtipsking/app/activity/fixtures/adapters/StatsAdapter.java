package com.bettingtipsking.app.activity.fixtures.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.model.StatsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bumptech.glide.Glide;

import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.LineupViewHolder> {

    Context context;
    List<StatsModel> list;

    public StatsAdapter(Context context, List<StatsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LineupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stats, parent, false);
        return new LineupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineupViewHolder holder, int position) {
        holder.type_tv.setText(list.get(position).getTitle());
        holder.teamAScore_tv.setText(list.get(position).getTeamAScore());
        holder.teamBScore_tv.setText(list.get(position).getTeamBScore());

        float scoreA= Float.parseFloat(list.get(position).getTeamAScore().replace("%", ""));
        float scoreB= Float.parseFloat(list.get(position).getTeamBScore().replace("%", ""));
        holder.pbHome.setProgress((int) Math.min(scoreA, 100));
        holder.pbAway.setProgress((int) Math.min(scoreB, 100));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LineupViewHolder extends RecyclerView.ViewHolder {
        TextView type_tv, teamAScore_tv, teamBScore_tv;
        ProgressBar pbHome, pbAway;
        public LineupViewHolder(@NonNull View itemView) {
            super(itemView);
            type_tv = itemView.findViewById(R.id.type_tv);
            teamAScore_tv = itemView.findViewById(R.id.teamAScore_tv);
            teamBScore_tv = itemView.findViewById(R.id.teamBScore_tv);
            pbHome = itemView.findViewById(R.id.pbHome);
            pbAway = itemView.findViewById(R.id.pbAway);
        }
    }
}
