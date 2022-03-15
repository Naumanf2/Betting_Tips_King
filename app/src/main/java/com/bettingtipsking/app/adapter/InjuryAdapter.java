package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.Model.InjuryModel;
import com.bettingtipsking.app.Model.NewsModel;
import com.bettingtipsking.app.R;

import java.util.ArrayList;
import java.util.List;

public class InjuryAdapter extends RecyclerView.Adapter<InjuryAdapter.ViewHolder> {
    Context context;
    List<InjuryModel> injuryList = new ArrayList<>();

    public InjuryAdapter(Context context, List<InjuryModel> injuryList) {
        this.context = context;
        this.injuryList = injuryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_injury_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InjuryModel model = injuryList.get(position);
        holder.playerName.setText(model.getPlayerName());
    }

    @Override
    public int getItemCount() {
        return injuryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.tvItemInjuryPlayerName);
        }
    }
}
