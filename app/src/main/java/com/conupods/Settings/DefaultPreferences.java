package com.conupods.Settings;

import android.content.SharedPreferences;

import com.conupods.R;

public class DefaultPreferences {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPrefEdit;

    public DefaultPreferences(SharedPreferences preferences) {
        mPreferences = preferences;
        mPrefEdit = mPreferences.edit();
    }

    public void setDefaultPreferencesForSettingsPage() {
        //Default settings preferences
        mPrefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.escalators), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.stairs), true).apply();
    }
}
