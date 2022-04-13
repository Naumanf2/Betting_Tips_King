package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ItemFavouritLeagueBinding;
import com.bettingtipsking.app.Helper.callback.FavouritLeagueInterfaceCallBack;
import com.bettingtipsking.app.model.FavouritLeagueModel;
import com.bettingtipsking.app.ui.follow.FollowTeamActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavouriteLeaguesAdapter extends RecyclerView.Adapter<FavouriteLeaguesAdapter.ViewHolder> {
    Context context;
    List<FavouritLeagueModel> list;
    FavouritLeagueInterfaceCallBack clickCallBack;

    public FavouriteLeaguesAdapter(Context context, List<FavouritLeagueModel> list, FavouritLeagueInterfaceCallBack clickCallBack) {
        this.context = context;
        this.list = list;
        this.clickCallBack = clickCallBack;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteLeaguesAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_favourit_league, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteLeaguesAdapter.ViewHolder holder, int position) {
        FavouritLeagueModel favouritLeagueModel = list.get(position);
        holder.binding.textTeamName.setText(favouritLeagueModel.getName());
        holder.binding.textCountry.setText(favouritLeagueModel.getCountry());
        Glide.with(context).load(favouritLeagueModel.getLogo()).into(holder.binding.imgProfile);

        holder.binding.textTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, FollowTeamActivity.class);
                myIntent.putExtra("LeagueID", list.get(holder.getAdapterPosition()).getId());
                System.out.println("League ID"+list.get(holder.getAdapterPosition()).getId());
                context.startActivity(myIntent);
            }
        });

        if (favouritLeagueModel.getFavStatus() == 0)
            holder.binding.imageStar.setImageDrawable(context.getDrawable(R.drawable.icon_star_border));
        else
            holder.binding.imageStar.setImageDrawable(context.getDrawable(R.drawable.icon_star));

        holder.binding.imageStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.favouritClick(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavouritLeagueBinding binding;

        public ViewHolder(ItemFavouritLeagueBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
