package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.databinding.ItemNewsRecyclerviewBinding;
import com.bettingtipsking.app.model.NewsModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.model.news.Data;
import com.bettingtipsking.app.ui.home.news.NewsDetailsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    List<Data> list;

    public NewsAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_news_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("call call");
        holder.binding.textNewsTitle.setText(list.get(position).getIntroduction());
        holder.binding.textNewsDate.setText(list.get(position).getUpdated_at());

        holder.binding.cardView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("introduction", list.get(pos).getIntroduction());
            intent.putExtra("date", list.get(pos).getUpdated_at());
            intent.putExtra("localTeam", list.get(pos).getLocalteam());
            intent.putExtra("visitorTeam", list.get(pos).getVisitorteam());
            context.startActivity(intent);
        });
    }

    public void setData(List<Data> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNewsRecyclerviewBinding binding;

        public ViewHolder(@NonNull ItemNewsRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
