package com.bettingtipsking.app.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.QuickHelp;
import com.bettingtipsking.app.model.CountriesModel;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.others.CountriesFetcher;
import com.bettingtipsking.app.databinding.ActivityPhoneLoginBinding;
import com.bettingtipsking.app.ui.home.HomeActivity;
import com.bettingtipsking.app.viewmodel.AuthLoginViewModel;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPhoneLoginBinding binding;
    private AuthLoginViewModel viewModel;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private String mCountryIso;
    private String mCountryName;
    private String mCountryDialCode;
    private PhoneNumberUtil mPhoneUtil = PhoneNumberUtil.getInstance();
    private CountriesModel mSelectedCountry;
    private CountriesFetcher.CountryList mCountries;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private String mPhoneNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);
        viewModel = new ViewModelProvider(this).get(AuthLoginViewModel.class);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        initializeViews();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("PHONE_LOGIN", "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                updateUI(credential);
                viewModel.loginWithCredentials(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("PHONE_LOGIN", "onVerificationFailed", e);

                mVerificationInProgress = false;



                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    ErrorCode();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    QuickHelp.showSimpleToast(getApplication(),"Quota exceeded.");

                    ErrorCode();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("PHONE_LOGIN", "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                binding.viewPagerVertical.setCurrentItem(1);

                setTimer();
                startTimer();
                binding.currentMobileNumber.setText(mPhoneNumber);
            }
        };

        viewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(PhoneLoginActivity.this, HomeActivity.class));
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(PhoneLoginActivity.this, HomeActivity.class));
                QuickHelp.showSimpleToast(getApplication(), "Something is wrong");
            }
        });
    }

    private void updateUI(PhoneAuthCredential credential) {
        if (credential != null) {
            if (credential.getSmsCode() != null) {
                binding.inputOtpWrapper.setText(credential.getSmsCode());
            } else {
                binding.inputOtpWrapper.setText("Verified");
            }
        }
    }

    public void initializeViews(){

        binding.btnRequestSms.setText("Next");
        binding.btnRequestSms.setEnabled(true);
        binding.btnRequestSms.setVisibility(View.VISIBLE);
        binding.layoutVerificationSv.setVisibility(View.VISIBLE);
        binding.numberPhoneLayoutSv.setVisibility(View.VISIBLE);
        binding.viewPagerVertical.setVisibility(View.VISIBLE);
        hideKeyboard();

        mCountries = CountriesFetcher.getCountries(this);

        int defaultIdx = 0;

        String countryISO = getApplicationContext().getResources().getConfiguration().locale.getCountry().toUpperCase();

        if (!countryISO.isEmpty()){

            defaultIdx = mCountries.indexOfIso(countryISO);

        } else {

            defaultIdx = mCountries.indexOfIso("US");
        }

        //int defaultIdx = mCountries.indexOfIso(countryISO);
        mSelectedCountry = mCountries.get(defaultIdx);
        binding.countryCode.setText(mSelectedCountry.getDial_code());
        binding.countryName.setText(mSelectedCountry.getName());
        binding.shortDescriptionPhone.setText(String.format("%s %s %s", "Click on", mSelectedCountry.getDial_code(), "to choose your country"));
        setHint();

        binding.btnRequestSms.setOnClickListener(this);
        binding.countryCode.setOnClickListener(this);
        binding.btnVerifyOtp.setOnClickListener(this);
        binding.btnRequestSms.setOnClickListener(this);
        binding.btnChangeNumber.setOnClickListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        binding.viewPagerVertical.setAdapter(adapter);

        binding.inputOtpWrapper.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    verificationOfCode();
                }
            }
        });


        if (binding.viewPagerVertical.getCurrentItem() == 1) {
            setOnKeyboardCodeDone();

        } else {
            setOnKeyboardDone();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.numberPhone.getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }



    private void setHint() {

        if (binding.numberPhone != null && mSelectedCountry != null && mSelectedCountry.getCode() != null) {
            Phonenumber.PhoneNumber phoneNumber = mPhoneUtil.getExampleNumberForType(mSelectedCountry.getCode(), PhoneNumberUtil.PhoneNumberType.MOBILE);
            if (phoneNumber != null) {
                String internationalNumber = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                String finalPhone = internationalNumber.substring(mSelectedCountry.getDial_code().length());
                binding.numberPhone.setHint(finalPhone);
                int numberLength = internationalNumber.length();
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(numberLength);
                binding.numberPhone.setFilters(fArray);

            }
        }

    }


    @SuppressWarnings("unused")
    public Phonenumber.PhoneNumber getPhoneNumber() {
        try {
            String iso = null;
            if (mSelectedCountry != null) {
                iso = mSelectedCountry.getCode();
            }
            String phone = binding.countryCode.getText().toString().concat( binding.numberPhone.getText().toString());
            return mPhoneUtil.parse(phone, iso);
        } catch (NumberParseException ignored) {
            return null;
        }
    }


    @SuppressWarnings("unused")
    public boolean isValid() {
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        return phoneNumber != null && mPhoneUtil.isValidNumber(phoneNumber);
    }

    public void setOnKeyboardDone() {
        binding.numberPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
            }
            return false;
        });
    }

    public void setOnKeyboardCodeDone() {
        binding.numberPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                verificationOfCode();
            }
            return false;
        });
    }

    private void startPhoneNumberVerification(String country, String mobile) {

        mPhoneNumber = mobile;


        binding.btnRequestSms.setText("Wait For SMS");
        binding.btnRequestSms.setEnabled(false);

        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,   // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS,    // Unit of timeout
                this,       // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        viewModel.loginWithCredentials(credential);

    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    public void ErrorCode(){

        QuickHelp.showSimpleToast(getApplication(),"Something was wrong!");
        binding.btnRequestSms.setText("Next");
        binding.btnRequestSms.setEnabled(true);
        hideKeyboard();
    }
    // [END resend_verification]


    private void verificationOfCode() {
        hideKeyboard();
        String code = binding.inputOtpWrapper.getText().toString().trim();
        if (!code.isEmpty()) {

            //doLogin(code);
            verifyPhoneNumberWithCode(mVerificationId, code);

        } else {
            QuickHelp.showSimpleToast(getApplication(), "Please enter your verification code");
        }
    }


    private void validateInformation() {
        hideKeyboard();
        Phonenumber.PhoneNumber phoneNumber = getPhoneNumber();
        if (phoneNumber != null) {
            String phoneNumberFinal = mPhoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            if (isValid()) {
                String internationalFormat = phoneNumberFinal.replace("-", "");
                String finalResult = internationalFormat.replace(" ", "");
                //Constant.setMobileNumber(this, finalResult);
                //requestForSMS(finalResult, mSelectedCountry.getName());
                startPhoneNumberVerification(mSelectedCountry.getName(), finalResult);
            } else {
                binding.numberPhone.setError("Enter a valid mobile phone");
            }
        } else {
            binding.numberPhone.setError("Enter a valid mobile phone");
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x011) {
               binding.numberPhone.setEnabled(true);
                binding.numberPhoneLayoutSv.pageScroll(View.FOCUS_DOWN);
                String codeIso = data.getStringExtra("countryIso");
                int defaultIdx = mCountries.indexOfIso(codeIso);
                mSelectedCountry = mCountries.get(defaultIdx);
                binding.countryCode.setText(mSelectedCountry.getDial_code());
                binding.countryName.setText(mSelectedCountry.getName());
                mCountryName = mSelectedCountry.getName();
                mCountryDialCode = mSelectedCountry.getDial_code();
                mCountryIso = mSelectedCountry.getCode();
                binding.shortDescriptionPhone.setText(String.format("%s %s %s", "click on", mSelectedCountry.getDial_code(), "to choose your country"));
                setHint();
            }
        }
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request_sms:
                validateInformation();
                break;

            case R.id.country_code:
                Intent mIntent = new Intent(this, CountryActivity.class);
                startActivityForResult(mIntent, 0x011);
                break;

            case R.id.btn_verify_otp:
                verificationOfCode();
                break;

            case R.id.Resend:
                resendVerificationCode(mPhoneNumber, mResendToken);
                break;

            case R.id.btn_change_number:
                binding.viewPagerVertical.setCurrentItem(0);
                binding.btnRequestSms.setText("Next");
                binding.btnRequestSms.setEnabled(true);
                break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTimer() {
        binding.TimeCount.setVisibility(View.VISIBLE);
        int time = 1;
        totalTimeCountInMilliseconds = 60 * time * 1000;

    }

    private void startTimer() {
        binding.TimeCount.setVisibility(View.VISIBLE);
        countDownTimer = new BaxPayCounter(totalTimeCountInMilliseconds, 500).start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void resumeTimer() {
        binding.TimeCount.setVisibility(View.VISIBLE);
        countDownTimer = new BaxPayCounter(totalTimeCountInMilliseconds, 500).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.numberPhoneLayout;
                    break;
                case 1:
                    resId = R.id.layout_verification;
                    break;
            }
            return findViewById(resId);
        }
    }

    public class BaxPayCounter extends CountDownTimer {

        BaxPayCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long leftTimeInMilliseconds) {
            long seconds = leftTimeInMilliseconds / 1000;
            binding.TimeCount.setText(String.format("%s:%s", String.format(Locale.getDefault(), "%02d", seconds / 60), String.format(Locale.getDefault(), "%02d", seconds % 60)));
            //textViewShowTime.setText(String.format(Locale.getDefault(), "%02d", seconds / 60) + ":" + String.format(Locale.getDefault(), "%02d", seconds % 60));
        }

        @Override
        public void onFinish() {
            binding.TimeCount.setVisibility(View.GONE);
            binding.Resend.setVisibility(View.VISIBLE);
        }
    }
}

