package com.bettingtipsking.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.model.VideosModel;
import com.bettingtipsking.app.R;

import java.util.ArrayList;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    List<VideosModel> videosList = new ArrayList<>();

    public VideosAdapter(List<VideosModel> videosList) {

        this.videosList = videosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_videos_landscape, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideosModel model = videosList.get(position);
        holder.tvNewsVideoTitle.setText(model.getNewsTitle());
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnPlayVideo;
        TextView tvNewsVideoTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnPlayVideo = itemView.findViewById(R.id.btnItemVideoPlay);
            tvNewsVideoTitle = itemView.findViewById(R.id.tvItemVideoTitle);
        }
    }
}
