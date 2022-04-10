package com.bettingtipsking.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.Helper.adapter_interfaces.ClickCallBack;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.Room.favLeague.FavLeagueModel;
import com.bettingtipsking.app.databinding.ItemFollowteamsBinding;
import com.bettingtipsking.app.model.league.Response;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavouriteLeaguesAdapter extends RecyclerView.Adapter<FavouriteLeaguesAdapter.ViewHolder> {
    Context context;
    List<FavLeagueModel> favLeagueModelList;
    FavLeagueModel favLeagueModel;
    ClickCallBack clickCallBack;

    public FavouriteLeaguesAdapter(Context context, List<FavLeagueModel> favLeagueModelList, ClickCallBack clickCallBack) {
        this.context = context;
        this.favLeagueModelList = favLeagueModelList;
        this.clickCallBack = clickCallBack;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteLeaguesAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_followteams, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteLeaguesAdapter.ViewHolder holder, int position) {
        favLeagueModel = favLeagueModelList.get(position);
        holder.binding.textTeamName.setText(favLeagueModel.getName());
        holder.binding.textCountry.setText(favLeagueModel.getCountry());
        Glide.with(context).load(favLeagueModel.getLogo()).into(holder.binding.imgProfile);
        if (favLeagueModel.getFavStatus() == 1)
            holder.binding.imageStar.setImageDrawable(context.getDrawable(R.drawable.icon_star));
        else
            holder.binding.imageStar.setImageDrawable(context.getDrawable(R.drawable.icon_star_border));

        holder.binding.imageStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.favStatus(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return favLeagueModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFollowteamsBinding binding;

        public ViewHolder(ItemFollowteamsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
