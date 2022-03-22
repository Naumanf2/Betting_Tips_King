package com.bettingtipsking.app.ui.home.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityNewsDetailsBinding;

public class NewsDetailsActivity extends AppCompatActivity {
    ActivityNewsDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);

        Intent intent = getIntent();
        if (intent != null) {
            binding.textNewsTitle.setText(intent.getStringExtra("introduction"));
            binding.textDate.setText(intent.getStringExtra("date"));
            binding.textLocalTeam.setText(intent.getStringExtra("localTeam"));
            binding.textVisitorTeam.setText(intent.getStringExtra("visitorTeam"));
        }

        binding.imageBack.setOnClickListener(v -> {finish();});

        binding.imageShare.setOnClickListener(v -> {
            if (intent !=null)
                shareNews(intent.getStringExtra("introduction"),"Introduction: \n\n"+intent.getStringExtra("introduction")+"\n\nLocal Team: \n\n"+intent.getStringExtra("localTeam")+
                        "\n\nVisitor Team: \n\n"+intent.getStringExtra("visitorTeam"));
        });
    }

    public void shareNews(String subject,String body) {
        Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
        txtIntent .setType("text/plain");
        txtIntent .putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        txtIntent .putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(txtIntent ,"Share"));
    }
}