package com.bettingtipsking.app.activity.fixtures.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.activity.fixtures.model.EventsModel;
import com.bettingtipsking.app.activity.fixtures.model.TeamWithEventsModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context context;
    List<EventsModel> list;

    public EventAdapter(Context context, List<EventsModel> list) {
        this.context = context;
        this.list = list;

        Log.e("received events size: ", String.valueOf(list.size()));
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventsModel model = list.get(position);
        if (model.getType().equals(context.getString(R.string.card))){
            holder.rl1.setVisibility(View.VISIBLE);
            holder.ll2.setVisibility(View.GONE);
            holder.rl11.setVisibility(View.GONE);

            holder.time_tv.setText(model.getTimeElapsed()+"'");
            holder.player_tv.setText(model.getPlayerName());
            if (model.getDetails().equals(context.getString(R.string.yellow_card))){
                holder.iv.setBackgroundColor(Color.YELLOW);
            }else{
                holder.iv.setBackgroundColor(Color.RED);
            }
        }else if (model.getType().equals(context.getString(R.string.subst))){
            holder.time_tv1.setText(model.getTimeElapsed()+"'");
            holder.player_tv1.setText(model.getAssistName());
            holder.player_tv11.setText(model.getPlayerName());

            holder.rl1.setVisibility(View.GONE);
            holder.ll2.setVisibility(View.VISIBLE);
            holder.rl11.setVisibility(View.GONE);
        }else if (model.getType().equals(context.getString(R.string.goal))){
            holder.time_tv11.setText(model.getTimeElapsed()+"'");
            holder.player_tv111.setText(model.getPlayerName());

            holder.rl1.setVisibility(View.GONE);
            holder.ll2.setVisibility(View.GONE);
            holder.rl11.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamTitle, time_tv, time_tv1, time_tv11, player_tv, player_tv1, player_tv11, player_tv111;
        ImageView ivTeamFlag, iv;
        RecyclerView rvEvents;
        RelativeLayout rl1, rl11;

        LinearLayout ll2;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvTeamTitle = itemView.findViewById(R.id.team_title_tv);
//            ivTeamFlag = itemView.findViewById(R.id.team_flag_iv);
//            rvEvents = itemView.findViewById(R.id.events_rv);

            rl1 = itemView.findViewById(R.id.rl1);
            time_tv = itemView.findViewById(R.id.time_tv);
            iv = itemView.findViewById(R.id.iv);
            player_tv = itemView.findViewById(R.id.player_tv);

            ll2 = itemView.findViewById(R.id.ll2);
            time_tv1 = itemView.findViewById(R.id.time_tv1);
            player_tv1 = itemView.findViewById(R.id.player_tv1);
            player_tv11 = itemView.findViewById(R.id.player_tv11);

            rl11 = itemView.findViewById(R.id.rl11);
            time_tv11 = itemView.findViewById(R.id.time_tv11);
            player_tv111 = itemView.findViewById(R.id.player_tv111);
        }
    }
}