package com.conupods.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_page);

        //Top Menu Button

        Button done = findViewById(R.id.done2);
        done.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, MapsActivity.class)));

        Button personalPage = findViewById(R.id.toggle2_1);
        personalPage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, SettingsPersonalActivity.class)));

        Button infoPage = findViewById(R.id.toggle2_2);
        infoPage.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, SettingsInfoActivity.class)));

        //My transit options

        CheckBox metro = findViewById(R.id.metro);
        metro.setOnClickListener(view -> {
            if (metro.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox train = findViewById(R.id.train);
        train.setOnClickListener(view -> {
            if (train.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox bus = findViewById(R.id.bus);
        train.setOnClickListener(view -> {
            if (bus.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox concordiaShuttle = findViewById(R.id.concordiaShuttle);
        train.setOnClickListener(view -> {
            if (concordiaShuttle.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        //My indoor options

        CheckBox elevators = findViewById(R.id.elevators);
        elevators.setOnClickListener(view -> {
            if (elevators.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox escalators = findViewById(R.id.escalators);
        escalators.setOnClickListener(view -> {
            if (escalators.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox stairs = findViewById(R.id.stairs);
        stairs.setOnClickListener(view -> {
            if (stairs.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox accessibilityInfo = findViewById(R.id.accessibilityInfo);
        escalators.setOnClickListener(view -> {
            if (accessibilityInfo.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });

        CheckBox stepFreeTrips = findViewById(R.id.stepFreeTrips);
        escalators.setOnClickListener(view -> {
            if (stepFreeTrips.isChecked()) {
                //Implement functionality
            } else {
                //Implement functionality
            }
        });
    }
}
