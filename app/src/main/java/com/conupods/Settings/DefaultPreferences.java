package com.conupods.Settings;

import android.content.SharedPreferences;

import com.conupods.R;

public class DefaultPreferences {

    public static void setDefaultPreferences(SharedPreferences preferences) {

        SharedPreferences.Editor prefEdit = preferences.edit();

        //Default preferences
        prefEdit.putBoolean(String.valueOf(R.id.metro), true).apply();
        prefEdit.putBoolean(String.valueOf(R.id.bus), true).apply();
        prefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), true).apply();
        prefEdit.putBoolean(String.valueOf(R.id.escalators), true).apply();
        prefEdit.putBoolean(String.valueOf(R.id.stairs), true).apply();
    }
}
