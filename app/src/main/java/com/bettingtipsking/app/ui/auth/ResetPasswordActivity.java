package com.bettingtipsking.app.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivityResetPasswordBinding;
import com.bettingtipsking.app.viewmodel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        binding.setResetViewModel(viewModel);

        viewModel.getMutableLiveData().observe(this, integer -> {
            if (integer == 0) {
                QuickHelp.goToActivityWithNoClean(ResetPasswordActivity.this, ConfirmEmailActivity.class);
            }else {
                QuickHelp.showSimpleToast(getApplication(),"Something is wrong");
            }
        });

        viewModel.getProgressMutableLiveData().observe(this,integer -> {
            if (integer==0){
                binding.progressBar.setVisibility(View.VISIBLE);
            }else if (integer==1){
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}