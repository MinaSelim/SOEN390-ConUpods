package com.conupods.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

public class SettingsInfoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page_info);
        //Top Menu buttons
        Button done = findViewById(R.id.done3);
        Button personalPage = findViewById(R.id.toggle3_1);
        Button defaultPage = findViewById(R.id.toggle3_2);
        done.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsInfoActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });
        personalPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsInfoActivity.this, SettingsPersonalActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });
        defaultPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsInfoActivity.this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });
    }
}
