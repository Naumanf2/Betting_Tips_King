package com.bettingtipsking.app.adapter;

import static com.bettingtipsking.app.Helper.HelperClass.getCurrentDate;
import static com.bettingtipsking.app.Helper.HelperClass.isSubValid;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.ui.home.home.HomeFragment;
import com.bettingtipsking.app.model.HomelPredictionsModel;
import com.bettingtipsking.app.R;

import java.util.List;

public class HomePredictionsAdapter extends RecyclerView.Adapter<HomePredictionsAdapter.ViewHolder> {
    Context context;
    List<HomelPredictionsModel> list;
    HomeFragment homeFragment;
    int size = 1;

    public HomePredictionsAdapter(HomeFragment homeFragment, Context context, List<HomelPredictionsModel> list) {
        this.homeFragment = homeFragment;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_predication, parent, false);
        return new HomePredictionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.txt_league.setText(list.get(position).getLeague_name());
        holder.txt_date.setText(list.get(position).getMatch_date());
        holder.txt_time.setText(list.get(position).getMatch_time());
        holder.txt_home_team.setText(list.get(position).getHome_team());
        holder.txt_away_team.setText(list.get(position).getAway_team());

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




        if (list.get(position).getMatch_status().equals("won")){
            holder.img_predictions.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_check_24));
        }else if (list.get(position).getMatch_status().equals("lost")){
            holder.img_predictions.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_clear_24));
        }else if (list.get(position).getMatch_status().equals("pending")){
            System.out.println("pending call");
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
        if (list.size() > 5) {
            if (isSubValid(context)) {
                return list.size();
            } else {
                if (!homeFragment.dateCheck) return list.size();
                else {
                    String currentDate = getCurrentDate();
                    if (homeFragment.finalDate.equals(currentDate)) {
                        size = 5;
                        return 5;
                    } else {
                        return list.size();
                    }
                }
            }
        }else {
            size = list.size();
            return list.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*CardView subscription_cv;*/
        TextView txt_league, txt_date, txt_time, txt_home_team, txt_home_score, txt_away_team,
                txt_away_score, txt_predications, txt_odd_value/*,txt_bitnow*/;

/*
        ImageView img_like;
*/
        ImageView img_share;
        ImageView img_predictions;
//        ImageView img_cloud;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
/*
            subscription_cv = itemView.findViewById(R.id.subscription_cv);
*/
            txt_league = itemView.findViewById(R.id.txt_league);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_home_team = itemView.findViewById(R.id.txt_home_team);
            txt_home_score = itemView.findViewById(R.id.txt_home_score);
/*
            txt_bitnow = itemView.findViewById(R.id.txt_bitnow);
*/
            txt_odd_value = itemView.findViewById(R.id.txt_odd_value);

            txt_away_team = itemView.findViewById(R.id.txt_away_team);
            txt_away_score = itemView.findViewById(R.id.txt_away_score);
            txt_predications = itemView.findViewById(R.id.txt_predications);
/*
            img_like = itemView.findViewById(R.id.img_like);
*/
            img_share = itemView.findViewById(R.id.img_share);
            img_predictions = itemView.findViewById(R.id.img_predictions);
//            img_cloud = itemView.findViewById(R.id.img_cloud);

        }
    }
}
