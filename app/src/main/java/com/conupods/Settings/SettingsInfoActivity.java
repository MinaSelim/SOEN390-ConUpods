package com.conupods.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

public class SettingsInfoActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_page_info);

        Button done = findViewById(R.id.done3);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsInfoActivity.this, MapsActivity.class));
            }
        });

        Button personalPage = findViewById(R.id.toggle3_1);
        personalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsInfoActivity.this, SettingsPersonalActivity.class));
            }
        });

        Button defaultPage = findViewById(R.id.toggle3_2);
        defaultPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsInfoActivity.this, SettingsActivity.class));
            }
        });

    }
}
