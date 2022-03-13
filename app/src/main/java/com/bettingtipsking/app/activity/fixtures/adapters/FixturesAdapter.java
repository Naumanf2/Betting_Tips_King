package com.bettingtipsking.app.activity.fixtures.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.activity.fixtures.model.FixturesModel;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.FixturesViewHolder> {

    Context context;
    List<FixturesModel> list;
    ItemClickListener listener;
    int type;


    public FixturesAdapter(Context context, List<FixturesModel> list, ItemClickListener listener, int type) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.type = type;
    }

    @NonNull
    @Override
    public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_fixtures, parent, false);
        return new FixturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesViewHolder holder, int position) {
        holder.tvLeagueTitle.setText(list.get(position).getLeagueName());
        Glide.with(context)
                .load(list.get(position).getLeagueLogo())
                .into(holder.ivLeagueFlag);

        MatchesAdapter adapter = new MatchesAdapter(context, list.get(position).getMatchesList(),listener, type, position);
        holder.rvMatches.setLayoutManager(new LinearLayoutManager(context));
        holder.rvMatches.setAdapter(adapter);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FixturesViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeagueTitle;
        ImageView ivLeagueFlag;
        RecyclerView rvMatches;
        public FixturesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLeagueFlag = itemView.findViewById(R.id.league_flag_iv);
            tvLeagueTitle = itemView.findViewById(R.id.league_title_tv);
            rvMatches = itemView.findViewById(R.id.matches_rv);

        }
    }
}
