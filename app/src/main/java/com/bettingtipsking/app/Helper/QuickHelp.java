package com.bettingtipsking.app.Helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bettingtipsking.app.adapter.CountriesAdapter;
import com.bettingtipsking.app.model.CountriesModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuickHelp {

    public static void goToActivityAndFinish(Activity currentActivity, Class<?> otherActivity) {
        Intent mainIntent = new Intent(currentActivity, otherActivity);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(mainIntent);
        currentActivity.finish();
    }

    public static void goToActivityWithNoClean(Activity currentActivity, Class<?> otherActivity) {
        Intent mainIntent = new Intent(currentActivity, otherActivity);
        currentActivity.startActivity(mainIntent);
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (connectivityManager != null) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static boolean validateEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static void openGmail(Application application){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            application.getApplicationContext().startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            QuickHelp.showSimpleToast(application,"There is no email client installed.");
        }
    }

    public static void showSimpleToast(Application application, String message){
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show();
    }

    public static int getColor(Context context, int id) {
        if (isAndroid5()) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }
    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void setCountryAdapter(Context context, List<CountriesModel> list, CountriesAdapter countriesAdapter){

        try {
            JSONArray countries = new JSONArray(loadJSONAllCountriesFromAsset(context));

            for (int i = 0; i < countries.length(); i++) {

                JSONObject country = (JSONObject) countries.get(i);

                list.add(new CountriesModel(country.getString("name"), country.getString("code"), country.getString("dial_code")));

                countriesAdapter.setCountries(list);
            }

        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    private static String loadJSONAllCountriesFromAsset(Context mContext) {
        String json;
        try {
            InputStream is = mContext.getAssets().open("all_country_phones.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = new String(buffer, StandardCharsets.UTF_8);
            } else {
                json = new String(buffer, "UTF-8");
            }

            //Log.v("COUNTRIES ALL", json);

        } catch (IOException ex) {
            ex.printStackTrace();

            //Log.v("COUNTRIES ALL", ex.getLocalizedMessage());

            return null;
        }
        return json;
    }
}
