package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.binding.textDate.setText(date.substring(0, 10));

        if (!String.valueOf(model.getGoals().getHome()).equals("null")) {
            holder.binding.textTeamHomeScore.setText("" + model.getGoals().getHome());
            holder.binding.textTeamAwayScore.setText("" + model.getGoals().getAway());
        } else {
            holder.binding.textTeamHomeScore.setText("-");
            holder.binding.textTeamAwayScore.setText("-");
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

        String shortStatus = list.get(position).getFixture().getStatus().getShort();
        if (shortStatus.equalsIgnoreCase("live")) {
            holder.binding.spinKitWave.setVisibility(View.VISIBLE);
        } else {
            holder.binding.spinKitWave.setVisibility(View.GONE);
        }



        if (shortStatus.equalsIgnoreCase("TBD")){

        }else if (shortStatus.equalsIgnoreCase("")){
            shortStatusLogic(holder,"Time To Be Defined",0);
        }
        else if (shortStatus.equalsIgnoreCase("NS")){
            shortStatusLogic(holder,"Not Started",0);

        }
        else if (shortStatus.equalsIgnoreCase("1H")){
            shortStatusLogic(holder,"First Half, Kick Off",1);
        }
        else if (shortStatus.equalsIgnoreCase("HT")){
            shortStatusLogic(holder,"Halftime",1);
        }
        else if (shortStatus.equalsIgnoreCase("2H")){
            shortStatusLogic(holder,"2nd Half Started",1);
        }
        else if (shortStatus.equalsIgnoreCase("ET")){
            shortStatusLogic(holder,"Extra Time",0);

        }
        else if (shortStatus.equalsIgnoreCase("P")){
            shortStatusLogic(holder,"Penalty In Progress",0);
        }
        else if (shortStatus.equalsIgnoreCase("FT")){
            shortStatusLogic(holder,"Match Finished",0);
        }
        else if (shortStatus.equalsIgnoreCase("AET")){
            shortStatusLogic(holder,"Match Finished After Extra Time",0);
        }
        else if (shortStatus.equalsIgnoreCase("PEN")){
            shortStatusLogic(holder,"Match Finished After Penalty",0);
        }
        else if (shortStatus.equalsIgnoreCase("BT")){
            shortStatusLogic(holder,"Break Time (in Extra Time",0);
        }
        else if (shortStatus.equalsIgnoreCase("SUSP")){
            shortStatusLogic(holder,"Match Suspended",0);
        }
        else if (shortStatus.equalsIgnoreCase("INT")){
            shortStatusLogic(holder,"Match Interrupted",0);
        }
        else if (shortStatus.equalsIgnoreCase("PST")){
            shortStatusLogic(holder,"Match Postponed",0);
        }
        else if (shortStatus.equalsIgnoreCase("CANC")){
            shortStatusLogic(holder,"Match Cancelled",0);
        }
        else if (shortStatus.equalsIgnoreCase("ABD")){
            shortStatusLogic(holder,"Match Abandoned",0);
        }
        else if (shortStatus.equalsIgnoreCase("WO")){
            shortStatusLogic(holder,"WalkOver",0);
        }
        else if (shortStatus.equalsIgnoreCase("LIVE")){
            shortStatusLogic(holder,"Live",1);
        }


    }

    private void shortStatusLogic(MatchesViewHolder holder, String message, int logic){
        holder.binding.textMatchStatus.setText(message);

        if (logic==1)
            holder.binding.spinKitWave.setVisibility(View.VISIBLE);
            else
            holder.binding.spinKitWave.setVisibility(View.GONE);

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
