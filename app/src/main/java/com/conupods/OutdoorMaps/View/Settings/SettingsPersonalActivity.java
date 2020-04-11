package com.conupods.OutdoorMaps.View.Settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.conupods.App;
import com.conupods.Calendar.CalendarObject;
import com.conupods.Calendar.CalendarSynchronization;
import com.conupods.MapsActivity;
import com.conupods.R;

import java.util.List;

public class SettingsPersonalActivity extends AppCompatActivity {

    private static final String TAG = "SETTINGS_PERSONAL_ACTIVITY";
    private static final int CALENDAR_PERMISSION_REQUEST_CODE = 1235;
    private static final String CALENDAR_READ_PERMISSION = Manifest.permission.READ_CALENDAR;
    private CalendarSynchronization mCalendarSynchronization = new CalendarSynchronization(CALENDAR_READ_PERMISSION, SettingsPersonalActivity.this, CALENDAR_PERMISSION_REQUEST_CODE);

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
        //my Account options event
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

        linkedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disable underlying buttons while pop up window is acitve
                linkedAccount.setEnabled(false);
                myAccount.setEnabled(false);
                myAccountBox.setEnabled(false);

                //get a reference to the already created settings layout
                RelativeLayout settingsLayout = (RelativeLayout) findViewById(R.id.layout_settings_personal);
                //inflate (create) another copy of our custom layout
                LayoutInflater inflater = getLayoutInflater();
                View calendarLayout = inflater.inflate(R.layout.settings_calendar_popup, settingsLayout, false);
                calendarLayout.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.shade));
                // get all calendars from user's account
                List<CalendarObject> calendars = mCalendarSynchronization.getAllCalendars();
                // inner container of calendarLayout where calendar buttons will be added dynamically
                LinearLayout container = calendarLayout.findViewById(R.id.dynamic_container);

                for (CalendarObject c : calendars) {
                    View row = inflater.inflate(R.layout.button_row, null);
                    container.addView(row);
                    Button calendarButton = row.findViewById(R.id.button_row);
                    calendarButton.setText(c.getDisplayName());
                }

                // add pop up calendarLayout to the main layout
                settingsLayout.addView(calendarLayout);

                //instantiate static buttons
                Button mClosePopupBtn = (Button) calendarLayout.findViewById(R.id.close_popup);
                mClosePopupBtn.requestFocus();

                //close the popup window on button click
                mClosePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mPopupWindow.dismiss();
                        settingsLayout.removeView(calendarLayout);
                        linkedAccount.setEnabled(true);
                        myAccount.setEnabled(true);
                        myAccountBox.setEnabled(true);
                    }
                });

            }
        });


    }


    // getting calendar permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult is called");
        if (requestCode == CALENDAR_PERMISSION_REQUEST_CODE) {
            mCalendarSynchronization.setCalendarPermissionsGranted(false);
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Calendar Permissions Failed " + i);
                        return;
                    }
                }
                Log.d(TAG, "Calendar Permissions Granted");
                mCalendarSynchronization.setCalendarPermissionsGranted(true);
                Log.d(TAG, "Calling calendarTryout from onRequestPermissionsResult()");
                mCalendarSynchronization.getAllCalendars();
            }
        } else {
            Log.d(TAG, "Permissions failed due to unexpected request code: " + requestCode);
        }

    }

}
