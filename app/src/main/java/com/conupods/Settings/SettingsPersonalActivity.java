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
            String email = "";
            if (!hasFocus) {
                email = String.valueOf(myAccount.getText());
                prefEdit.putString(String.valueOf(myAccount.getId()), email).apply();
                TextView googleCalendar = findViewById(R.id.googleCalendar);
                if (!email.equals("")) {
                    //TODO Implement connection with GoogleCalendar
                    prefEdit.putString(String.valueOf(googleCalendar.getId()), "Connected").apply();
                    googleCalendar.setText(preferences.getString(String.valueOf(googleCalendar.getId()), null));
                } else {
                    prefEdit.putString(String.valueOf(googleCalendar.getId()), "Not Connected").apply();
                    googleCalendar.setText(preferences.getString(String.valueOf(googleCalendar.getId()), null));
                }
            }
        });

        Button myAccountBox = findViewById(R.id.myAccountBox);
        myAccountBox.setOnClickListener(view -> {
            myAccount.requestFocus();
        });

        Button linkedAccount = findViewById(R.id.linkedAccount);
        linkedAccount.setOnClickListener(view -> {
            myAccount.requestFocus();
        });
    }
}
