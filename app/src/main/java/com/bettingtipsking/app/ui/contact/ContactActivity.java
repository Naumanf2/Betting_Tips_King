package com.bettingtipsking.app.ui.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bettingtipsking.app.Helper.SendMailTask;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {
    ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String title = binding.textInputLayoutName.getEditText().getText().toString();
                    String body = binding.editTextMessage.getText().toString();
                    new SendMailTask(ContactActivity.this).execute("officeuser667@gmail.com",
                            "NM5074667", "support@bettingtips-king.com", title, body);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                binding.editTextMessage.setText("");
                binding.textInputLayoutName.getEditText().setText("");
            }
        });

    }
}