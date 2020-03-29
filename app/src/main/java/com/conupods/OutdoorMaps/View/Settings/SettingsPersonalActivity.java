package com.conupods.OutdoorMaps.View.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.MapsActivity;
import com.conupods.R;

public class SettingsPersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page_personal);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = preferences.edit();
        //Top Menu buttons
        Button done = findViewById(R.id.done1);
        Button defaultPage = findViewById(R.id.toggle1_1);
        Button infoPage = findViewById(R.id.toggle1_2);
        //Account Options buttons
        EditText myAccount = findViewById(R.id.email);
        Button myAccountBox = findViewById(R.id.myAccountBox);
        Button linkedAccount = findViewById(R.id.linkedAccount);
        //Top Menu events
        done.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        defaultPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        infoPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        //Account options event
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
        myAccountBox.setOnClickListener(view -> myAccount.requestFocus());
        linkedAccount.setOnClickListener(view -> myAccount.requestFocus());
    }
}
