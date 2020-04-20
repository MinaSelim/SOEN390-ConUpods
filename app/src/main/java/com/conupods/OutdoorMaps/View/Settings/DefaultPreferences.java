package com.conupods.OutdoorMaps.View.Settings;

import android.content.SharedPreferences;

import com.conupods.R;

/**
 * @author felix
 */
public class DefaultPreferences {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPrefEdit;

    /**
     * This constructor set the default preferences
     */
    public DefaultPreferences(SharedPreferences preferences) {
        mPreferences = preferences;
        mPrefEdit = mPreferences.edit();
    }

    /**
     * This method add every default preference to the preference list
     */
    public void setDefaultPreferencesForSettingsPage() {
        //Default settings preferences TRUE
        mPrefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.escalators), true).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.stairs), true).apply();
        //Default settings preferences FALSE
        mPrefEdit.putBoolean(String.valueOf(R.id.elevators), false).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.stepFreeTrips), false).apply();
        mPrefEdit.putBoolean(String.valueOf(R.id.accessibilityInfo), false).apply();
    }
}
