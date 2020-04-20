package com.conupods.Calendar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.conupods.App;
import com.conupods.OutdoorMaps.View.Settings.SettingsPersonalActivity;

import java.util.ArrayList;
import java.util.List;

/*
This class holds the logic to configure the specific user calendar
the app should use in the Calendar event notifier feature
 */
public class CalendarSynchronization {
    private Activity mActivity;
    private boolean mCalendarPermissionsGranted = false;
    public int mCalendarPermissionRequestCode;
    private String mCalendarReadPermissions;

    private static final String TAG = "CALENDAR_SYNCH";

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 1;
    private Cursor mCalendarCursor;

    public CalendarSynchronization(String mCalendarReadPermissions, Activity activity, int mCalendarPermissionRequestCode) {
        this.mCalendarReadPermissions = mCalendarReadPermissions;
        this.mActivity = activity;
        this.mCalendarPermissionRequestCode = mCalendarPermissionRequestCode;
        ;
    }

    //returns a list of all calendars visible on the user's account
    public List<CalendarObject> getAllCalendars() {
        List<CalendarObject> calendarList = new ArrayList<>();

        if (!mCalendarPermissionsGranted) { getCalendarPermission(); }

        mCalendarCursor= initCalendarCursor();

        if (mCalendarCursor != null) {
            calendarList = getCalendarList();
            mCalendarCursor.close();
        }
        return calendarList;
    }

    private void getCalendarPermission() {
        Log.d(TAG, "Getting Calendar Permissions");
        /** After android Marshmellow release, we need to explicitly check for
         * permissions such as location permissions*/
        String[] permissions = {mCalendarReadPermissions};
        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), mCalendarReadPermissions) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ_CALENDAR_PERMISSION given");
            mCalendarPermissionsGranted = true;
        } else {
            ActivityCompat.requestPermissions(mActivity, permissions, mCalendarPermissionRequestCode);
        }
    }

    private List<CalendarObject> getCalendarList() {
        List<CalendarObject> calendars = new ArrayList<>();
        // Use the cursor to step through the returned records
        while (mCalendarCursor.moveToNext()) {
            String calendarID = mCalendarCursor.getString(PROJECTION_ID_INDEX);
            String displayName = mCalendarCursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            //add to calendars list
            calendars.add(new CalendarObject(calendarID, displayName));
            // print in log
            Log.d(TAG, "calendarID: " + calendarID + " displayName: " + displayName);
        }
        return calendars;
    }

    public void setCalendarPermissionsGranted(boolean calendarPermissionsGranted) {
        this.mCalendarPermissionsGranted = calendarPermissionsGranted;
    }
    //must be delcared in an Activities class for proper error handling of permissions

    private Cursor initCalendarCursor() {
        // Projection array: columns to return for each row. (Making indices instead of doing dynamic lookups improves performance.)
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 1
        };
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        getCalendarPermission();
        if (ContextCompat.checkSelfPermission(App.getContext().getApplicationContext(), mCalendarReadPermissions) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {mCalendarReadPermissions};
            ActivityCompat.requestPermissions(mActivity,permissions, mCalendarPermissionRequestCode);
        }
        Cursor calendarCursor = contentResolver.query(uri, projection, null, null, null);
        return calendarCursor;
    }
}
