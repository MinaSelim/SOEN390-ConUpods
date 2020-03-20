package com.conupods.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.conupods.OutdoorMaps.View.MainActivity;
import com.conupods.R;

public class DefaultPreferences {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPrefEdit;

    public DefaultPreferences(MainActivity current){
        mPreferences = current.getApplicationContext().getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        mPrefEdit = mPreferences.edit();
    }

    public void setDefaultPreferencesForSettingsPage() {

        //Default settings preferences
        mPrefEdit.putBoolean(String.valueOf(R.id.metro), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.bus), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.escalators), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.stairs), true).apply();
    }
}
