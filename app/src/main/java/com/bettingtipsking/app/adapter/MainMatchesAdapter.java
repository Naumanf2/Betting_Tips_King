package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.model.MainMatchesModel;
import com.bettingtipsking.app.R;

import java.util.List;

public class MainMatchesAdapter extends RecyclerView.Adapter<MainMatchesAdapter.viewHolder> {

    Context context;
    List<MainMatchesModel> mainMatchesList;

    RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();

    public MainMatchesAdapter(Context context, List<MainMatchesModel> mainMatchesList) {
        this.context = context;
        this.mainMatchesList = mainMatchesList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_multircl, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
MainMatchesModel model= mainMatchesList.get(position);

        LinearLayoutManager layoutManager=new LinearLayoutManager(holder.recyclerView.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(model.getMatchesList().size());
        SubMatchesAdapter adapter=new SubMatchesAdapter(context,model.getMatchesList());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setRecycledViewPool(viewPool);


    }

    @Override
    public int getItemCount() {
        return mainMatchesList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView=itemView.findViewById(R.id.sub_matches_rcl);
        }
    }
}
