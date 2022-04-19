package com.bettingtipsking.app.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bettingtipsking.app.activity.MainActivity;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.model.fixtures.Home;
import com.bettingtipsking.app.ui.home.HomeActivity;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Map<String, String> extraData = remoteMessage.getData();
        int screen_id = Integer.valueOf(extraData.get("screen_id"));
        System.out.println("screen id is "+screen_id);
        if (screen_id == 0) {
            String id = extraData.get("id");
            String status = extraData.get("status");
            String league_name = extraData.get("league_name");
            String match_id = extraData.get("match_id");
            String home_team = extraData.get("home_team");
            String away_team = extraData.get("away_team");
            String odd_value = extraData.get("odd_value");
            String home_score = extraData.get("home_score");
            String away_score = extraData.get("away_score");
            String match_minute = extraData.get("match_minute");
            String game_prediction = extraData.get("game_prediction");
            String match_date = extraData.get("match_date");
            String match_status = extraData.get("match_status");
            String match_time = extraData.get("match_time");
            showNotificationForHome(title, body,screen_id, id, status, league_name, match_id,
                    home_team, away_team, odd_value, home_score, away_score, match_minute, game_prediction,
                    match_date, match_status, match_time);

        } else if (screen_id == 1) {
            int fixture_id = Integer.parseInt(extraData.get("fixture_id"));
            int league_id = Integer.parseInt(extraData.get("league_id"));
            int team_away_id = Integer.parseInt(extraData.get("team_away_id"));
            int team_home_id = Integer.parseInt(extraData.get("team_home_id"));
            showNotificationForMatch(title, body, screen_id,fixture_id, league_id, team_away_id, team_home_id);

        } else if (screen_id == 2) {
            String created_at = extraData.get("created_at");
            String fixture_id = extraData.get("fixture_id");
            String introduction = extraData.get("introduction");
            String localteam = extraData.get("localteam");
            String type = extraData.get("type");
            String updated_at = extraData.get("updated_at");
            String visitorteam = extraData.get("visitorteam");

            showNotificationForNews(title,body,screen_id,created_at,fixture_id,introduction,localteam,type,updated_at,visitorteam);

        } else if (screen_id == 3) {

        }


    }

    private void showNotificationForHome(String title, String body, int screen_id,  String id, String status, String league_name, String match_id, String home_team, String away_team, String odd_value, String home_score, String away_score, String match_minute, String game_prediction, String match_date, String match_status, String match_time) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("screen_id", screen_id);
        intent.putExtra("id", id);
        intent.putExtra("status", status);
        intent.putExtra("league_name", league_name);
        intent.putExtra("match_id", match_id);
        intent.putExtra("home_team", home_team);
        intent.putExtra("away_team", away_team);
        intent.putExtra("odd_value", odd_value);
        intent.putExtra("home_score", home_score);
        intent.putExtra("away_score", away_score);
        intent.putExtra("match_minute", match_minute);
        intent.putExtra("game_prediction", game_prediction);
        intent.putExtra("match_date", match_date);
        intent.putExtra("match_status", match_status);
        intent.putExtra("match_time", match_time);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "111")
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLights(Color.GREEN, 3000, 3000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1111", "NOTIFICATION_CHANNEL_NAME_MATCH", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId("1111");
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0, notificationBuilder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void showNotificationForMatch(String title, String body,int screen_id, int fixture_id, int league_id, int team_away_id, int team_home_id) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("screen_id", screen_id);
        intent.putExtra("fixture_id", fixture_id);
        intent.putExtra("league_id", league_id);
        intent.putExtra("team_home_id", team_home_id);
        intent.putExtra("team_away_id", team_away_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "111")
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLights(Color.GREEN, 3000, 3000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("111", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId("111");
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0, notificationBuilder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void showNotificationForNews(String title, String body,int screen_id,String created_at, String fixture_id, String introduction, String localteam, String type, String updated_at, String visitorteam) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("screen_id", screen_id);
        intent.putExtra("created_at", created_at);
        intent.putExtra("fixture_id", fixture_id);
        intent.putExtra("introduction", introduction);
        intent.putExtra("localteam", localteam);
        intent.putExtra("type", type);
        intent.putExtra("updated_at", updated_at);
        intent.putExtra("visitorteam", visitorteam);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "111")
                .setSmallIcon(R.drawable.ic_icon)
                .setContentText(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setLights(Color.GREEN, 3000, 3000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("111", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationBuilder.setChannelId("111");
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0, notificationBuilder.build());
        } else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }


}
