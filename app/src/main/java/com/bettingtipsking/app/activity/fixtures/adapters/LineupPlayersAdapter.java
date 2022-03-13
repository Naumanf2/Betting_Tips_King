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
import com.bettingtipsking.app.activity.fixtures.model.TeamWithPlayers;
import com.bumptech.glide.Glide;

import java.util.List;

public class LineupPlayersAdapter extends RecyclerView.Adapter<LineupPlayersAdapter.LineupViewHolder> {

    Context context;
    List<TeamWithPlayers> list;

    public LineupPlayersAdapter(Context context, List<TeamWithPlayers> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LineupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        return new LineupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineupViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getPlayerNumber() +"-"+list.get(position).getPlayerName());
        Glide.with(context)
                .load(list.get(position).getPlayerPhoto())
                .into(holder.ivPlayer);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LineupViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber;
        ImageView ivPlayer;
        public LineupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name_tv);
            tvNumber = itemView.findViewById(R.id.number_tv);
            ivPlayer = itemView.findViewById(R.id.iv);
        }
    }
}
