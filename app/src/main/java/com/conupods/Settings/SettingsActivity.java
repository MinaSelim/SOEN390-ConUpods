package com.conupods.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Editor prefEdit = preferences.edit();

        Button done = findViewById(R.id.done2);
        Button personalPage = findViewById(R.id.toggle2_1);
        Button infoPage = findViewById(R.id.toggle2_2);

        CheckBox metro = findViewById(R.id.metro);
        CheckBox train = findViewById(R.id.train);
        CheckBox bus = findViewById(R.id.bus);
        CheckBox concordiaShuttle = findViewById(R.id.concordiaShuttle);

        CheckBox elevators = findViewById(R.id.elevators);
        CheckBox escalators = findViewById(R.id.escalators);
        CheckBox stairs = findViewById(R.id.stairs);
        CheckBox accessibilityInfo = findViewById(R.id.accessibilityInfo);
        CheckBox stepFreeTrips = findViewById(R.id.stepFreeTrips);

        setCheckedBoxes(preferences);

        //Top Menu Button

        done.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, MapsActivity.class)));
        personalPage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, SettingsPersonalActivity.class)));
        infoPage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, SettingsInfoActivity.class)));

        //My transit options

        metro.setOnClickListener(view -> {
            if (metro.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.metro), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.metro), false).apply();
            }
        });

        train.setOnClickListener(view -> {
            if (train.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.train), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.train), false).apply();
            }
        });

        bus.setOnClickListener(view -> {
            if (bus.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.bus), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.bus), false).apply();
            }
        });

        concordiaShuttle.setOnClickListener(view -> {
            if (concordiaShuttle.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.concordiaShuttle), false).apply();
            }
        });

        //My indoor options

        elevators.setOnClickListener(view -> {
            if (elevators.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.elevators), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.elevators), false).apply();
            }
        });

        escalators.setOnClickListener(view -> {
            if (escalators.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.escalators), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.escalators), false).apply();
            }
        });

        stairs.setOnClickListener(view -> {
            if (stairs.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.stairs), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.stairs), false).apply();
            }
        });

        accessibilityInfo.setOnClickListener(view -> {
            if (accessibilityInfo.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.accessibilityInfo), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.accessibilityInfo), false).apply();
            }
        });

        stepFreeTrips.setOnClickListener(view -> {
            if (stepFreeTrips.isChecked()) {
                prefEdit.putBoolean(String.valueOf(R.id.stepFreeTrips), true).apply();
            } else {
                prefEdit.putBoolean(String.valueOf(R.id.stepFreeTrips), false).apply();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCheckedBoxes(preferences);
    }

    protected void setCheckedBoxes(SharedPreferences preferences){

        CheckBox metro = findViewById(R.id.metro);
        CheckBox train = findViewById(R.id.train);
        CheckBox bus = findViewById(R.id.bus);
        CheckBox concordiaShuttle = findViewById(R.id.concordiaShuttle);
        CheckBox elevators = findViewById(R.id.elevators);
        CheckBox escalators = findViewById(R.id.escalators);
        CheckBox stairs = findViewById(R.id.stairs);
        CheckBox accessibilityInfo = findViewById(R.id.accessibilityInfo);
        CheckBox stepFreeTrips = findViewById(R.id.stepFreeTrips);

        if(preferences.getBoolean(String.valueOf(R.id.metro), false)){
            metro.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.train), false)){
            train.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.bus), false)){
            bus.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.concordiaShuttle), false)){
            concordiaShuttle.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.elevators), false)){
            elevators.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.escalators), false)){
            escalators.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.stairs), false)){
            stairs.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.accessibilityInfo), false)){
            accessibilityInfo.setChecked(true);
        }

        if(preferences.getBoolean(String.valueOf(R.id.stepFreeTrips), false)){
            stepFreeTrips.setChecked(true);
        }
    }
}
