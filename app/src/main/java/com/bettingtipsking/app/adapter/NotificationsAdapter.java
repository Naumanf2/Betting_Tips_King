package com.bettingtipsking.app.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bettingtipsking.app.model.NotificationsModel;
import com.bettingtipsking.app.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    List<NotificationsModel> notificationList = new ArrayList<>();
    Context context;


    public NotificationsAdapter(Context context, List<NotificationsModel> notificationList) {

        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notifications, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        NotificationsModel model = notificationList.get(position);
        holder.textNotification.setText(model.getNotificationText());


    }

    @Override
    public int getItemCount() {

        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNotification, tvDate;
        ImageView imgNotification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textNotification = itemView.findViewById(R.id.text_item_notification);
            tvDate = itemView.findViewById(R.id.date_item_notification);
            imgNotification = itemView.findViewById(R.id.img_item_notification);

        }
    }
}
