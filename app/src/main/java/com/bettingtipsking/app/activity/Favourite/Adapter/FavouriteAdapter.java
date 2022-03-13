package com.bettingtipsking.app.activity.Favourite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.Helper.PredictionsInterface;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.Predictions;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    Context context;
    List<Predictions> list;
    PredictionsInterface predictionsInterface;


    public FavouriteAdapter(Context context, List<Predictions> list,PredictionsInterface predictionsInterface) {
        this.context = context;
        this.list = list;
        this.predictionsInterface = predictionsInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_prediction, parent, false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_league.setText(list.get(position).getLeague_name());
        holder.txt_date.setText(list.get(position).getMatch_date());
        holder.txt_time.setText(list.get(position).getMatch_time());
        holder.txt_home_team.setText(list.get(position).getHome_team());
        holder.txt_away_team.setText(list.get(position).getAway_team());

        holder.img_like.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_fill_blue_24));


        if (TextUtils.isEmpty(list.get(position).getTeam_home_score())) {
            holder.txt_home_score.setText("-");
        } else {
            holder.txt_home_score.setText(list.get(position).getTeam_home_score());
        }


        if (TextUtils.isEmpty(list.get(position).getTeam_away_score())) {
            holder.txt_away_score.setText("-");
        } else {
            holder.txt_away_score.setText(list.get(position).getTeam_away_score());
        }

        holder.txt_predications.setText(list.get(position).getGame_prediction());
        holder.txt_odd_value.setText(list.get(position).getOdd_value());


    holder.img_like.setOnClickListener(v -> {
        predictionsInterface.DeteteCall(holder.getAdapterPosition());
    });


        if (list.get(position).getMatch_status().equals("won")){
            holder.img_predictions.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_check_24));
        }else if (list.get(position).getMatch_status().equals("lost")){
            holder.img_predictions.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_clear_24));
        }else if (list.get(position).getStatus().equals("pending")){
            holder.img_predictions.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_wifi_protected_setup_24));
        }

        holder.img_share.setOnClickListener(v -> {

            String text = "League: "+list.get(position).getLeague_name()+"\n " +
                    "Home Team: "+list.get(position).getHome_team()+" ---  "+list.get(position).getTeam_home_score()+"\n " +
                    "Away Team: "+list.get(position).getAway_team()+" ---  "+list.get(position).getTeam_away_score()+"\n " +
                    "Match Date: "+list.get(position).getMatch_date()+"\n " +
                    "Match Time: "+list.get(position).getMatch_time()+"\n " +
                    "Odd Value: "+list.get(position).getOdd_value()+"\n " +
                    "Sport Type: "+list.get(position).getSport_type()+"\n " +
                    "Prediction: "+list.get(position).getGame_prediction();

            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent2, "Share via"));
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Predictions> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView txt_league, txt_date, txt_time, txt_home_team, txt_home_score, txt_away_team,
                txt_away_score, txt_predications,txt_odd_value;
        ImageView img_like;

        ImageView img_share;

        ImageView img_predictions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            txt_league = itemView.findViewById(R.id.txt_league);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_home_team = itemView.findViewById(R.id.txt_home_team);
            txt_home_score = itemView.findViewById(R.id.txt_home_score);
            txt_odd_value = itemView.findViewById(R.id.txt_odd_value);

            txt_away_team = itemView.findViewById(R.id.txt_away_team);
            txt_away_score = itemView.findViewById(R.id.txt_away_score);
            txt_predications = itemView.findViewById(R.id.txt_predications);
            img_like = itemView.findViewById(R.id.img_like);
            img_share = itemView.findViewById(R.id.img_share);
            img_predictions = itemView.findViewById(R.id.img_predictions);

        }
    }
}
