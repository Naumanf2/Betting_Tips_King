package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.model.VideosMainModel;
import com.bettingtipsking.app.R;

import java.util.List;

public class VideosMainAdapter extends RecyclerView.Adapter<VideosMainAdapter.ViewHolder> {

    private Context context;
    private List<VideosMainModel> newsVideosMainList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public VideosMainAdapter(Context context, List<VideosMainModel> newsVideosMainList) {
        this.context = context;
        this.newsVideosMainList = newsVideosMainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_videos_main_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideosMainModel model = newsVideosMainList.get(position);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        layoutManager.setInitialPrefetchItemCount(model.getNewsVideosList().size());
        VideosAdapter childItemAdapter = new VideosAdapter(model.getNewsVideosList());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(childItemAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);


    }

    @Override
    public int getItemCount() {
        return newsVideosMainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.itemVideosRV);
        }
    }
}
