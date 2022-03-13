package com.bettingtipsking.app.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bettingtipsking.app.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperClass {
//    public static String HOST = "api-football-beta.p.rapidapi.com";
    public static String HOST = "api-football-v1.p.rapidapi.com";
    public static String KEY = "1006731426msh719f2a4da8f9a28p1d7ef2jsn1bc8d19faa8f";
    public static String SHARED_PREFERENCE = "betting_tips_king";
    public static String IS_LOGIN = "is_login";
    public static String USER_ID = "user_id";
    public static String USER_NAME = "user_name";
    public static String USER_EMAIL = "user_email";
    public static String USER_PASSWORD = "user_password";
    public static String SUBSCRIPTION_PACKAGE = "subscription_package";
    public static String SUBSCRIPTION_DATE = "subscription_date";
    public static String REVIEW_TIME = "review_time";
    public static String FIXTURE_MODEL = "fixture_model";
    public static String FIXTURE_DETAIL_MODEL = "fixture_detail_model";

    public static int FIXTURES = 1;
    public static int H2H = 2;
//    public static String subDetails = "<b>How it works? </b><br/>";
    public static String subDetails = "Betting Tips King is a free application for every sports fan. Regular users get 5 free daily tips. In addition to these features, users can get VIP features with Betting Tips King's cutting edge predictions algorithm.\n\n" +
        "A VIP user can get all tips in the following time intervals:\n\n" +
        "$2 One week subscription\n" +
        "$8.49 One month subscription\n" +
        "$24.49 Three months subscription\n" +
        "$48.49 Six months subscription\n\n" +
        "Payments will be charged to your account  at confirmation of purchase\n" +
        "You can choose between one week,one month, three months, and six months VIP subscription to predictions done by the Betting Tips King's edge prediction algorithm\n" +
        "Betting Tips King VIP access prices can vary depending on your country\n" +
        "Subscriptions may be managed by the user and auto-renewal may be turned off by going to the user's account settings after purchase\n" +
        "Your subscription will automatically renew, unless you have turned off auto-renewal at least 24 hours before the end of the current period\n" +
        "When your subscription auto-renews, the same Account as the initial purchase will be charged the same amount as the initial purchase\n" +
        "After canceling your subscription, your subscription will remain active until the end of the purchased period";


    public static boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void reviewDialogue(Activity ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(REVIEW_TIME, new Date().getTime());
        editor.apply();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);

        final View mView = ctx.getLayoutInflater().inflate(R.layout.custom_layout_review_dialog, null);

        TextView btn_okay = mView.findViewById(R.id.okay);
        TextView tvLater = mView.findViewById(R.id.later_tv);
        RatingBar ratingBar = mView.findViewById(R.id.rating);

        mBuilder.setView(mView);
        final Dialog dialog = new Dialog(ctx, R.style.NewDialog);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        btn_okay.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            if (rating >= 4.0) {
                appRating(ctx);
                dialog.dismiss();
            }else{
                dialog.dismiss();
                ctx.finish();
            }
        });
        tvLater.setOnClickListener(v -> {
                dialog.dismiss();
                ctx.finish();
        });
    }

    public static void msgDialogue(Activity ctx) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);

        final View mView = ctx.getLayoutInflater().inflate(R.layout.custom_layout_alert_dialog, null);

        TextView close = mView.findViewById(R.id.close);
        mBuilder.setView(mView);
        final Dialog dialog = new Dialog(ctx, R.style.NewDialog);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    public static void appRating(Context context) {
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp: otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(otherAppActivity.applicationInfo.packageName, otherAppActivity.name);
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="+appId));
            context.startActivity(webIntent);
        }
    }

    public static void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(USER_NAME, "");
        editor.putString(USER_EMAIL, "");
        editor.putString(USER_PASSWORD, "");
        editor.putString(SUBSCRIPTION_PACKAGE, "null");
        editor.putString(SUBSCRIPTION_DATE, "null");
        editor.apply();
    }

    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        return currentDay+"."+String.format("%02d", currentMonth)+"."+String.format("%02d", currentYear);

    }

    public static boolean isSubValid(Context context){
        boolean isValid = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE);
        String subPackage = sharedPreferences.getString(SUBSCRIPTION_PACKAGE, "null");
        String subDate = sharedPreferences.getString(SUBSCRIPTION_DATE, "0");
        if (subPackage.equalsIgnoreCase("null") && subDate.equalsIgnoreCase("null")){
            isValid = false;
        }else {
            switch (subPackage) {
                case Config.SUBS_1_WEEK: {
                    long difference = new Date().getTime() - Long.parseLong(subDate);
                    isValid = difference <= (7 * 24 * 60 * 60 * 1000);
                    break;
                }
                case Config.SUBS_1_MONTH: {
                    long difference = new Date().getTime() - Long.parseLong(subDate);
                    isValid = difference <= (30L * 24 * 60 * 60 * 1000);
                    break;
                }
                case Config.SUBS_3_MONTHS: {
                    long difference = new Date().getTime() - Long.parseLong(subDate);
                    isValid = difference <= (90L * 24 * 60 * 60 * 1000);
                    break;
                }
                case Config.SUBS_6_MONTHS: {
                    long difference = new Date().getTime() - Long.parseLong(subDate);
                    isValid = difference <= (180L * 24 * 60 * 60 * 1000);
                    break;
                }
            }
        }

        return isValid;
    }
}
