package com.bettingtipsking.app.activity.fixtures.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BindingMethodAdapter {
    @BindingAdapter("loadImage")
    static public void loadImage(ImageView iv, String url){
        Glide
             .with(iv.getContext())
             .load(url)
             .into(iv);
    }
    @BindingAdapter("removeNull")
    static public void removeNull(TextView tv, String team){
        String[] teams = team.split("-");
        String teamA = teams[0];
        String teamB = teams[1];
        if (teamA.equals("null")) teamA = "NA";
        if (teamB.equals("null")) teamB = "NA";
        if (teamA.equals("NA") && teamB.equals("NA")){
            String[] time1 = team.split("T");
            String time2 = time1[1].replace("+", "-");
            String[] time3 = time2.split("-");
            String[] time4 = time3[0].split(":");
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            String dateString = formatter.format(new Date(Long.parseLong(teams[2])));
            tv.setText(time4[0]+":"+time4[1]);
        }else {
            tv.setText(teamA + "-" + teamB);
        }
    }
    @BindingAdapter("checkNull")
    static public void checkNull(TextView tv, String name){
        if (name.equals("null"))
            tv.setVisibility(View.GONE);
        else {
            tv.setText("Ref:"+name);
            tv.setVisibility(View.VISIBLE);
        }
    }
    @BindingAdapter("checkLocationNull")
    static public void checkLocationNull(TextView tv, String location){
        if (location.equals("null,null"))
            tv.setVisibility(View.GONE);
        else {
            tv.setText(location);
            tv.setVisibility(View.VISIBLE);
        }
    }
}


