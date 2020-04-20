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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.conupods.App;
import com.conupods.Calendar.CalendarObject;
import com.conupods.Calendar.CalendarSynchronization;
import com.conupods.MapsActivity;
import com.conupods.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SettingsPersonalActivity extends AppCompatActivity {

    private static final String TAG = "SETTINGS_PERSONAL_ACTIVITY";
    private static final int CALENDAR_PERMISSION_REQUEST_CODE = 1235;
    private static final String CALENDAR_READ_PERMISSION = Manifest.permission.READ_CALENDAR;

    public static CalendarObject mSelectedCalendar;

    private List<Button> mRadioGroup = new ArrayList<>();
    private CalendarSynchronization mCalendarSynchronization = new CalendarSynchronization(CALENDAR_READ_PERMISSION, SettingsPersonalActivity.this, CALENDAR_PERMISSION_REQUEST_CODE);
    private TextView mGoogleCalendarTextView;
    private View mCalendarLayout;
    private RelativeLayout settingsLayout;

    private Button mMyAccountBox;
    private EditText mMyAccount;
    private Button mLinkedAccount;
    public static boolean mSettingsCalendarPermission = false;

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
        mMyAccount = findViewById(R.id.email);
        mMyAccountBox = findViewById(R.id.myAccountBox);
        mLinkedAccount = findViewById(R.id.linkedAccount);
        //Top Menu events
        done.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        defaultPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        infoPage.setOnClickListener(view -> startActivityIfNeeded(new Intent(SettingsPersonalActivity.this, SettingsInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), 0));
        //initialise
        mGoogleCalendarTextView = findViewById(R.id.googleCalendar);
        //TODO: not sure what this is supposed to do...
        //my Account options event
        mMyAccount.setOnFocusChangeListener((view, hasFocus) -> {
            String email = "";
            if (!hasFocus) {
                email = String.valueOf(mMyAccount.getText());
                prefEdit.putString(String.valueOf(mMyAccount.getId()), email).apply();
                if (email.endsWith("@gmail.com")) {
                    prefEdit.putString(String.valueOf(mGoogleCalendarTextView.getId()), "Connected").apply();
                    mGoogleCalendarTextView.setText(preferences.getString(String.valueOf(mGoogleCalendarTextView.getId()), null));
                } else {
                    prefEdit.putString(String.valueOf(mGoogleCalendarTextView.getId()), "Not Connected").apply();
                    mGoogleCalendarTextView.setText(preferences.getString(String.valueOf(mGoogleCalendarTextView.getId()), null));
                }
            }
        });

        mMyAccountBox.setOnClickListener(view -> mMyAccount.requestFocus());
        //check for permissions
        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), CALENDAR_READ_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {CALENDAR_READ_PERMISSION};
            ActivityCompat.requestPermissions(SettingsPersonalActivity.this,permissions, CALENDAR_PERMISSION_REQUEST_CODE);
        }
        if (mSelectedCalendar != null) {
            mGoogleCalendarTextView.setText("connected");
            mGoogleCalendarTextView.setTextColor(ContextCompat.getColor(App.getContext(), R.color.connected));
        }

        mLinkedAccount.setOnClickListener(v -> {
            showCalendarPopup();
            initPopupCloseButton();
        });
    }

    /*
    initiates static buttons of the calendar popup
     */
    private void initPopupCloseButton() {
        Button mClosePopupBtn = (Button) mCalendarLayout.findViewById(R.id.close_popup);
        mClosePopupBtn.requestFocus();
        //close the popup window on button click
        mClosePopupBtn.setOnClickListener(v -> {
            settingsLayout.removeView(mCalendarLayout);
            mLinkedAccount.setEnabled(true);
            mMyAccount.setEnabled(true);
            mMyAccountBox.setEnabled(true);
        });
    }

    /*
    pops up the calendar set up into view
    and initiates dynamic buttons
     */
    private void showCalendarPopup() {
        //disable underlying buttons while pop up window is active
        mLinkedAccount.setEnabled(false);
        mMyAccount.setEnabled(false);
        mMyAccountBox.setEnabled(false);

        if (mCalendarLayout == null) {
            // get all calendars from user's account
            List<CalendarObject> calendars = mCalendarSynchronization.getAllCalendars();
            initCalendarPopUp(calendars);
        }
        // add pop-up calendarLayout to the main layout
        settingsLayout.addView(mCalendarLayout);
    }

    private void initCalendarPopUp(List<CalendarObject> calendars) {
        //get a reference to the already created settings layout
        settingsLayout = (RelativeLayout) findViewById(R.id.layout_settings_personal);
        //inflate (create) a copy of our custom layout
        LayoutInflater inflater = getLayoutInflater();
        mCalendarLayout = inflater.inflate(R.layout.settings_calendar_popup, settingsLayout, false);
        mCalendarLayout.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.shade));

        // inner container of calendarLayout where calendar buttons will be added dynamically
        LinearLayout container = mCalendarLayout.findViewById(R.id.dynamic_container);

        //add a button for every visible user calendar
        for (CalendarObject c : calendars) {
            View row = inflater.inflate(R.layout.button_row, null);
            container.addView(row);
            Button calendarButton = row.findViewById(R.id.button_row);
            mRadioGroup.add(calendarButton);
            calendarButton.setText(c.getDisplayName());
            //check for stored calendar
            Boolean foundMatch = false;

            if(mSelectedCalendar!=null){
                if ((c.getCalendarID()).replaceAll("\\s+", "").equalsIgnoreCase((mSelectedCalendar.getCalendarID()).replaceAll("\\s+", ""))) {
                    foundMatch = true;
                }
            }

            //hightlight retreived selected option
            if (mSelectedCalendar != null && foundMatch) {
                markSelected(calendarButton);
            }

            calendarButton.setOnClickListener(v -> {
                mGoogleCalendarTextView.setText("connected");
                mGoogleCalendarTextView.setTextColor(ContextCompat.getColor(App.getContext(), R.color.connected));
                markSelected(calendarButton);
                mSelectedCalendar = c;
                writeToFile(mSelectedCalendar.getCalendarID(), App.getContext());

                Log.d(TAG, "SELECTED ACCOUNT: " + mSelectedCalendar.getDisplayName() + " ID: " + c.getCalendarID());
            });
        }
    }

    private void resetAllRadioNeutral() {
        for (Button button : mRadioGroup) {
            button.setBackground(ContextCompat.getDrawable(App.getContext(), R.drawable.bg_settings_cal_bttn_white));
        }
    }

    private void markSelected(Button button) {
        resetAllRadioNeutral();
        button.setBackground(ContextCompat.getDrawable(App.getContext(), R.drawable.bg_settings_cal_bttn_selected));
    }


    // getting calendar permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult is called");
        if (requestCode == CALENDAR_PERMISSION_REQUEST_CODE) {
            mSettingsCalendarPermission = false;
            mCalendarSynchronization.setCalendarPermissionsGranted(false);
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Calendar Permissions Failed " + i);
                        return;
                    }
                }
                Log.d(TAG, "Calendar Permissions Granted");
                mSettingsCalendarPermission = true;
                mCalendarSynchronization.setCalendarPermissionsGranted(true);
                Log.d(TAG, "Resume to calling calendarTryout");
                mCalendarSynchronization.getAllCalendars();
            }
        } else {
            mSettingsCalendarPermission = false;
            Log.d(TAG, "Permissions failed due to unexpected request code: " + requestCode);
        }

    }

    public CalendarSynchronization getCalendarSynchronization() {
        return mCalendarSynchronization;
    }

    public void writeToFile(String data, Context context) {
        try {
            Log.d(TAG, "about to write: " + data);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("calendar_data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
