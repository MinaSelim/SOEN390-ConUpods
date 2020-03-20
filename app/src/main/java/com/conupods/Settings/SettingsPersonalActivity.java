package com.conupods.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
        done.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });

        Button defaultPage = findViewById(R.id.toggle1_1);
        defaultPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });

        Button infoPage = findViewById(R.id.toggle1_2);
        infoPage.setOnClickListener(view -> {
            startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0);
        });

        EditText myAccount = findViewById(R.id.email);
        myAccount.setOnFocusChangeListener((view, hasFocus) -> {
            //Implement account connection
            if(!hasFocus)
                prefEdit.putString(String.valueOf(myAccount.getId()), String.valueOf(myAccount.getText())).apply();
        });

        Button myAccountBox = findViewById(R.id.myAccountBox);
        myAccountBox.setOnClickListener(view -> {
            myAccount.requestFocus();
        });

        Button linkedAccount = findViewById(R.id.linkedAccount);
        linkedAccount.setOnClickListener(view -> {
            //Implement linked account connection

            TextView connected = findViewById(R.id.connected);
            connected.setText("Connected");
        });
    }
}
