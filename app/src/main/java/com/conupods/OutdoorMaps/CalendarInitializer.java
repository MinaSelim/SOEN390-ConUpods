package com.conupods.OutdoorMaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.conupods.App;
import com.conupods.Calendar.CalendarObject;
import com.conupods.Calendar.Event;
import com.conupods.OutdoorMaps.View.Settings.SettingsPersonalActivity;
import com.conupods.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CalendarInitializer {
    private static final String TAG = "MapInitializer";
    private View mNextEventLayout;
    private RelativeLayout mHomeLayout;
    private TextView mNextEventdate;
    private TextView mNextEventTitle;
    private TextView mNextEventlocation;
    private TextView mNextEventTime;
    private LayoutInflater mInflater;
    private CalendarObject mCalendar;

    enum Notification {
        OPEN,
        UNOPEN,
        NONE
    }
    private static Notification mNotificationStatus = Notification.NONE;








    public void initNextEventButton(LayoutInflater inflater, RelativeLayout homeLayout) {
        mInflater = inflater;
        mHomeLayout = homeLayout;
        Button nextEventBttn = (Button) mHomeLayout.findViewById(R.id.calendarButton);

        initEventWindow();
        initNextEventPopUp();
        initEventExitButton(nextEventBttn);
        notifyOfUpcomingEvent(nextEventBttn);

        nextEventBttn.setOnClickListener(v -> {
            mNotificationStatus = Notification.OPEN;
            refreshNextEvent(nextEventBttn);
            showEventWindow();
        });
    }


    private void showEventWindow() {
        mHomeLayout.addView(mNextEventLayout);
    }

    private void refreshNextEvent(Button nextEventBttn) {
        initEventWindow();
        initNextEventPopUp();
        initEventExitButton(nextEventBttn);
    }

    /*
    inflate next event window
     */
    public void initEventWindow() {
        //inflate (create) a copy of our custom layout
        mNextEventLayout = mInflater.inflate(R.layout.window_next_event, mHomeLayout, false);
        //shade background
        mNextEventLayout.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.shade));

        mNextEventdate = (TextView) mNextEventLayout.findViewById(R.id.event_date);
        mNextEventTitle = (TextView) mNextEventLayout.findViewById(R.id.event_name);
        mNextEventlocation = (TextView) mNextEventLayout.findViewById(R.id.event_location);
        mNextEventTime = (TextView) mNextEventLayout.findViewById(R.id.event_time);


    }

    /*
      initiates close button of the calendar popup
       */
    private void initEventExitButton(Button nextEventBttn) {
        Button mCloseEventPopup = (Button) mNextEventLayout.findViewById(R.id.exitEventButton);
        mCloseEventPopup.requestFocus();
        //close the popup window on button click
        mCloseEventPopup.setOnClickListener(v -> {
            if (mNextEventLayout != null) {
                mHomeLayout.removeView(mNextEventLayout);
                nextEventBttn.setBackgroundResource(R.drawable.ic_next_event_button);
            } else {
                Log.d(TAG, "could not close next event window, view nextEventLayout is null");
            }
        });
    }

    /*
    set up text in next event window
     */
    private void initNextEventPopUp() {
        mCalendar = SettingsPersonalActivity.mSelectedCalendar;
        boolean isCalendarSelected = mCalendar != null;

        if (!isCalendarSelected) {
            mNextEventdate.setText("");
            mNextEventTitle.setText("");
            mNextEventlocation.setText("");
            mNextEventTime.setText("set up Google Calendar account in settings");
        } else if (!mCalendar.hasNextEvent()) {
            mNextEventdate.setText("");
            mNextEventTitle.setText("");
            mNextEventlocation.setText("");
            mNextEventTime.setText("No upcoming events in the next 24h");
        } else {
            Event nextEvent = mCalendar.getNextEvent();
            mNextEventdate.setText(nextEvent.getmNextEventDate());
            mNextEventTitle.setText(nextEvent.getmNextEventTitle());
            mNextEventTime.setText(nextEvent.getmNextEventStartTime() + "-" + nextEvent.getmNextEventEndTime());
            mNextEventlocation.setText(nextEvent.getmNextEventLocation());
        }
    }

    private void notifyOfUpcomingEvent(Button eventBttn) {
        mCalendar = SettingsPersonalActivity.mSelectedCalendar;
        if (mCalendar != null && mCalendar.hasNextEvent()) {
            Event nextEvent = mCalendar.getNextEvent();
            Log.d(TAG, "nextEvent.upcomingEventSoon(): " + nextEvent.upcomingEventSoon());
            if (nextEvent.upcomingEventSoon() && mNotificationStatus == Notification.NONE) {
                eventBttn.setBackgroundResource(R.drawable.ic_next_event_button_highlight);
                mNotificationStatus = Notification.UNOPEN;
            }
        }
    }

    /**
     * Read from local file calendar_data.txt
     * @param context
     * @return fileContent
     */
    public String readFromFile(Context context) {
        String fileContent = "";
        try {
            InputStream inputStream = context.openFileInput("calendar_data.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                fileContent = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d(TAG, "READ FROM FILE: " + fileContent);
        return fileContent;
    }

    /**
     * Query calendar provider table for a calendar with specific id
     * @param id
     * @param activity
     * @return savedCalendar
     */
    @SuppressLint("MissingPermission")
    public CalendarObject getCalendarFromPast(String id, Activity activity) {
        CalendarObject savedCalendar = null;
        //init cursor
        String[] projection = new String[]{
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME //0
        };
        String selection = "_id=?";
        String[] selectionArgs = new String[]{id};
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        if (getCalendarPermissions(activity)) {
            Cursor calendarCursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
            if (calendarCursor.moveToNext()) {
                String displayName = calendarCursor.getString(0);
                savedCalendar = new CalendarObject(id, displayName);
            }
        }
        return savedCalendar;
    }

    /**
     * resets the notification state to NONE
     */
    public void resetNotificationState() {
        mNotificationStatus = Notification.NONE;
    }

    /**
     * If calendar permission is not initially given, re-sends request for permission
     * @param activity
     * @return true if calendar permissions is given, false otherwise
     */
    public boolean getCalendarPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_CALENDAR};
            ActivityCompat.requestPermissions(activity, permissions, 1235);
        }

        return ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED;
    }
}
