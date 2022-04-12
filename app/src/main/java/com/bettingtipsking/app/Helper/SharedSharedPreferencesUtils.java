package com.bettingtipsking.app.Helper;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedSharedPreferencesUtils {

    SharedPreferences userSharedPref = getApplicationContext().getSharedPreferences(Config.USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    SharedPreferences.Editor userSharedPrefEditor = userSharedPref.edit();

    public void setString(String sharedName, String value){
        userSharedPrefEditor.putString(sharedName, value);
    }
    public String getString(String sharedName, String defaultValue){
        return userSharedPref.getString(sharedName,defaultValue);
    }

    public void setBoolean(String sharedName, boolean value){
        userSharedPrefEditor.putBoolean(sharedName, value);
    }
    public boolean getBoolean(String sharedName, boolean defaultValue){
        return userSharedPref.getBoolean(sharedName,defaultValue);
    }
    public void setInt(String sharedName, int value){
        userSharedPrefEditor.putInt(sharedName, value);
    }
    public int getInt(String sharedName, int defaultValue){
        return userSharedPref.getInt(sharedName,defaultValue);
    }
}


