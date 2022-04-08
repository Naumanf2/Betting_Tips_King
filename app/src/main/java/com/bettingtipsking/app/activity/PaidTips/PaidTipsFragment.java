package com.bettingtipsking.app.activity.PaidTips;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.bettingtipsking.app.Helper.Config;
import com.bettingtipsking.app.R;

import java.util.ArrayList;
import java.util.List;


public class PaidTipsFragment extends Fragment implements PurchasesUpdatedListener {

    private SkuDetails productModel;

    private BillingClient mBillingClient;
    private List<SkuDetails> mPaymentProductModels  = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_paid_tips, container, false);

        initPurchase();

        return view;
    }

    private void initPurchase() {


        mBillingClient = BillingClient.newBuilder(getContext()).setListener(this).enablePendingPurchases().build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                     initSKUPurchase();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


    }

    public void initSKUPurchase() {

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
                            mPaymentProductModels.addAll(skuDetailsList);
                            mPaymentProductModels.add(skuDetailsList.get(0));

                        } else {
                            //Something is wrong
                            Toast.makeText(getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        //todo error ocurred

                    }
                });
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
}