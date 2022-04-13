package com.bettingtipsking.app.activity.fixtures;

import static com.bettingtipsking.app.Helper.HelperClass.IS_LOGIN;
import static com.bettingtipsking.app.Helper.HelperClass.SHARED_PREFERENCE;
import static com.bettingtipsking.app.Helper.HelperClass.SUBSCRIPTION_DATE;
import static com.bettingtipsking.app.Helper.HelperClass.SUBSCRIPTION_PACKAGE;
import static com.bettingtipsking.app.Helper.HelperClass.USER_ID;
import static com.bettingtipsking.app.Helper.HelperClass.subDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.databinding.ActivitySubscriptionsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SubscriptionsActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    ActivitySubscriptionsBinding binding;
    private SkuDetails productModel;
    private BillingClient mBillingClient;

    private List<SkuDetails> mPaymentProductModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscriptions);
        initPurchase();


        binding.subDetailsTv.setText(subDetails);
        binding.btnWeekOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateSubscription(Config.SUBS_1_WEEK);
                if (isLogin()) {
                    if (mPaymentProductModels.size() > 0){
                        productModel = mPaymentProductModels.get(0);
                        initPurchaseFlow();
                    }
                }else {
                 //   startActivity(new Intent(SubscriptionsActivity.this, LoginActivity.class));
                }
            }
        });
        binding.btnMonthOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()){
                    if (mPaymentProductModels.size() > 0){
                        productModel = mPaymentProductModels.get(1);
                        initPurchaseFlow();
                    }
                }else {
                   // startActivity(new Intent(SubscriptionsActivity.this, LoginActivity.class));
                }
            }
        });
        binding.btnWeekThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    if (mPaymentProductModels.size() > 0){
                        productModel = mPaymentProductModels.get(2);
                        initPurchaseFlow();
                    }
                }else {
                 //   startActivity(new Intent(SubscriptionsActivity.this, LoginActivity.class));
                }

            }
        });
        binding.btnWeekSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    if (mPaymentProductModels.size() > 0){
                        productModel = mPaymentProductModels.get(3);
                        initPurchaseFlow();
                    }
                }else {
               //     startActivity(new Intent(SubscriptionsActivity.this, LoginActivity.class));
                }

            }
        });
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    private void initPurchase() {
        mBillingClient = BillingClient.newBuilder(SubscriptionsActivity.this).setListener(this).enablePendingPurchases().build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.e("TEST_ALL-BillSetup: ", "onBillingSetupFinished");
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    initSKUPurchase();
                }else {
                    Log.e("TEST_ALL-BILL Result", "NOT_OK");
//                    Toast.makeText(SubscriptionsActivity.this, "Something wrong 0", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                Log.e("TEST_ALL-BillSetup: ", "onBillingServiceDisconnected");
//                Toast.makeText(SubscriptionsActivity.this, "onBillingServiceDisconnected", Toast.LENGTH_SHORT).show();

                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


    }



    public void initSKUPurchase() {
        Log.e("TEST_ALL-initSKUPur", "initSKUPurchase");
        List<String> creditsPurchase = new ArrayList<>();
        creditsPurchase.add(Config.SUBS_1_WEEK);
        creditsPurchase.add(Config.SUBS_1_MONTH);
        creditsPurchase.add(Config.SUBS_3_MONTHS);
        creditsPurchase.add(Config.SUBS_6_MONTHS);

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(creditsPurchase).setType(BillingClient.SkuType.SUBS);
        mBillingClient.querySkuDetailsAsync(params.build(),
                (billingResult, skuDetailsList) -> {
                    // Process the result.

                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                        if (skuDetailsList != null && skuDetailsList.size() > 0){
                            Log.e("TEST_ALL-skuDetailsList", "size ok:"+skuDetailsList.size());
                            createList(skuDetailsList, skuDetailsList.get(0));

                        } else {
                            Log.e("TEST_ALL-skuDetailsList", "size not ok:"+skuDetailsList.size());
//                            Toast.makeText(this, "Size is "+skuDetailsList.size(), Toast.LENGTH_SHORT).show();

                            //todo something is wrong
                        }

                    } else {
                        Log.e("TEST_ALL-skuDetailsList", "BillingResponseCode.NOT OK");
//                        Toast.makeText(SubscriptionsActivity.this, "Something wrong 2", Toast.LENGTH_SHORT).show();
                        //todo something is wrong
                    }
                });
    }

    private void createList(List<SkuDetails> skuDetailsList, SkuDetails skuDetails) {

        mPaymentProductModels.clear();
        mPaymentProductModels.addAll(skuDetailsList);
        mPaymentProductModels.add(skuDetails);
        productModel = mPaymentProductModels.get(0);
    }

    public void initPurchaseFlow(){
        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(productModel)
                .build();
        mBillingClient.launchBillingFlow(SubscriptionsActivity.this, flowParams);

    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {

                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                    // Acknowledge purchase and grant the item to the user


                    switch (purchase.getSku()) {

                        case Config.SUBS_1_WEEK:
                            updateSubscription(Config.SUBS_1_WEEK);
                            break;
                        case Config.SUBS_1_MONTH:
                            updateSubscription(Config.SUBS_1_MONTH);
                            break;

                        case Config.SUBS_3_MONTHS:
                            updateSubscription(Config.SUBS_3_MONTHS);
                            break;

                        case Config.SUBS_6_MONTHS:
                            updateSubscription(Config.SUBS_6_MONTHS);
                            break;
                    }

                }
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(SubscriptionsActivity.this, "Payment process canceled", Toast.LENGTH_SHORT).show();

            // Handle an error caused by a user cancelling the purchase flow.


        } else {
            Toast.makeText(SubscriptionsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            // Handle any other error codes.
        }
    }

    public void updateSubscription(String packageName){
        String subscription_date = String.valueOf(new Date().getTime());
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(USER_ID, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SUBSCRIPTION_PACKAGE, packageName);
        editor.putString(SUBSCRIPTION_DATE, subscription_date);
        editor.apply();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> map = new HashMap<>();
        map.put("subscription_date", subscription_date);
        map.put("subscription_package", packageName);
        rootRef.child("Users").child(uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                Log.e("updateSubscription: ", "Updated");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}