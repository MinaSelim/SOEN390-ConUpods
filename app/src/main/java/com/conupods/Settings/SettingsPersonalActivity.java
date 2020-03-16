package com.conupods.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsPersonalActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_page_personal);

        Button done = findViewById(R.id.done1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsPersonalActivity.this, MapsActivity.class));
            }
        });

        Button defaultPage = findViewById(R.id.toggle1_1);
        defaultPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsPersonalActivity.this, SettingsActivity.class));
            }
        });

        Button infoPage = findViewById(R.id.toggle1_2);
        infoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsPersonalActivity.this, SettingsInfoActivity.class));
            }
        });

    }
}
