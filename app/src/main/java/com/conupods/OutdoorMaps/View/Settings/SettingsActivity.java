package com.conupods.OutdoorMaps.View.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.R;
import com.conupods.MapsActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        //Top menu buttons
        Button done = findViewById(R.id.done2);
        Button personalPage = findViewById(R.id.toggle2_1);
        Button infoPage = findViewById(R.id.toggle2_2);
        //Preferences buttons
        CheckBox[] preferenceButtons = {findViewById(R.id.concordiaShuttle), findViewById(R.id.elevators), findViewById(R.id.escalators),
                findViewById(R.id.stairs), findViewById(R.id.accessibilityInfo), findViewById(R.id.stepFreeTrips)};
        //Top menu events
        done.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        personalPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsActivity.this, SettingsPersonalActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        infoPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsActivity.this, SettingsInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        //Check Default preferences
        setCheckedBoxes();
        //My preferences events
        for (CheckBox preferenceButton : preferenceButtons) {
            preferenceButton.setOnClickListener(view -> changePreferences(preferenceButton, preferenceButtons));
        }
    }

    protected void changePreferences(CheckBox preference, CheckBox[] preferenceButtons) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Editor prefEdit = preferences.edit();
        if (preference.isChecked()) {
            prefEdit.putBoolean(String.valueOf(preference.getId()), true).apply();
        } else {
            prefEdit.putBoolean(String.valueOf(preference.getId()), false).apply();
        }

        for (CheckBox preferenceButton : preferenceButtons) {
            if (preferences.getBoolean(String.valueOf(preferenceButton.getId()), false))
                return;
        }
        prefEdit.putBoolean(String.valueOf(preferenceButtons[1].getId()), true).apply();
        checkBoxIfInPreference(preferenceButtons[1]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckedBoxes();
    }

    protected void setCheckedBoxes() {
        checkBoxIfInPreference(findViewById(R.id.concordiaShuttle));
        checkBoxIfInPreference(findViewById(R.id.elevators));
        checkBoxIfInPreference(findViewById(R.id.escalators));
        checkBoxIfInPreference(findViewById(R.id.stairs));
        checkBoxIfInPreference(findViewById(R.id.accessibilityInfo));
        checkBoxIfInPreference(findViewById(R.id.stepFreeTrips));
    }

    protected void checkBoxIfInPreference(CheckBox preference) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (preferences.getBoolean(String.valueOf(preference.getId()), false)) {
            preference.setChecked(true);
        }
    }
}
