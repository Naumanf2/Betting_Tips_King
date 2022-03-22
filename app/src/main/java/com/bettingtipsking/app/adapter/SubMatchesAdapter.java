package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.model.MatchesModel;
import com.bettingtipsking.app.R;

import java.util.List;

public class SubMatchesAdapter extends RecyclerView.Adapter<SubMatchesAdapter.viewHolder> {
    Context context;
    List<MatchesModel> list;

    public SubMatchesAdapter(Context context, List<MatchesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_matches, parent, false);
        return new viewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MatchesModel model= list.get(position);
        holder.txt.setText(model.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.textTeam1name);
        }
    }
}
