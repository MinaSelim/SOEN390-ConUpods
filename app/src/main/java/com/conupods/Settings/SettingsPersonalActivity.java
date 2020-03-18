package com.conupods.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsPersonalActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_page_personal);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = preferences.edit();

        Button done = findViewById(R.id.done1);
        done.setOnClickListener(view -> startActivity(new Intent(SettingsPersonalActivity.this, MapsActivity.class)));

        Button defaultPage = findViewById(R.id.toggle1_1);
        defaultPage.setOnClickListener(view -> startActivity(new Intent(SettingsPersonalActivity.this, SettingsActivity.class)));

        Button infoPage = findViewById(R.id.toggle1_2);
        infoPage.setOnClickListener(view -> startActivity(new Intent(SettingsPersonalActivity.this, SettingsInfoActivity.class)));

        Button myAccount = findViewById(R.id.myAccount);
        myAccount.setOnClickListener(view -> {
            //Implement account connection
            //Default email
            prefEdit.putString(String.valueOf(R.id.email), "defaultEmail@gmail.com").apply();
            TextView email = findViewById(R.id.email);
            email.setText(preferences.getString(String.valueOf(R.id.email), "No Email Found"));
        });

        Button linkedAccount = findViewById(R.id.linkedAccount);
        linkedAccount.setOnClickListener(view -> {
            //Implement linked account connection

            TextView connected = findViewById(R.id.connected);
            connected.setText("Connected");
        });
    }
}
