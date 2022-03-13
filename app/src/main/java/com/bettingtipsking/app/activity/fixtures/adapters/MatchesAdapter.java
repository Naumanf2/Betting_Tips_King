package com.bettingtipsking.app.activity.fixtures.adapters;

import static com.bettingtipsking.app.Helper.HelperClass.H2H;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bettingtipsking.app.activity.fixtures.model.MatchDetailsModel;
import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {

    Context context;
    List<MatchDetailsModel> list;
    ItemClickListener listener;
    int type;
    int parentPosition;

    public MatchesAdapter(Context context, List<MatchDetailsModel> list, ItemClickListener listener, int type, int parentPosition) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.type = type;
        this.parentPosition = parentPosition;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match_detail, parent, false);
        return new MatchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        MatchDetailsModel model = list.get(position);
        String goalHome = model.getGoalsHome();
        String goalAway = model.getGoalsAway();
        if (goalHome.equals("null")){
            goalHome = "NA";
        }
        if (goalAway.equals("null")){
            goalAway = "NA";
        }
        holder.tvGoal.setText(goalHome+"-"+goalAway);
        holder.tvHomeTeam.setText(model.getTeamHomeName());
        holder.tvAwayTeam.setText(model.getTeamAwayName());

     /*   GlideUrl glideUrl = new GlideUrl(model.getTeamHomeLogo(), new LazyHeaders.Builder()
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build());*/
        Glide.with(context).load(model.getTeamHomeLogo()).into(holder.ivHomeFlag);
        Glide.with(context).load(model.getTeamAwayLogo()).into(holder.ivAwayFlag);

        if (type == H2H) {
            holder.view.setVisibility(View.VISIBLE);
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(model.getFixtureDate().substring(0, model.getFixtureDate().length() - 15));
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
    TextView tvGoal, tvHomeTeam, tvAwayTeam, tvDate;
    View view;
    ImageView ivHomeFlag, ivAwayFlag;
    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        tvGoal = itemView.findViewById(R.id.goal_tv);
        tvHomeTeam = itemView.findViewById(R.id.home_team_tv);
        tvAwayTeam = itemView.findViewById(R.id.away_team_tv);
        ivHomeFlag = itemView.findViewById(R.id.home_flag_iv);
        ivAwayFlag = itemView.findViewById(R.id.away_flag_iv);
        view = itemView.findViewById(R.id.view);
        tvDate = itemView.findViewById(R.id.date_tv);
    }
}
