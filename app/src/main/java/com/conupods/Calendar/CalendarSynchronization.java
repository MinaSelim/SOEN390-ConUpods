package com.conupods.Calendar;

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

import java.util.ArrayList;
import java.util.List;

public class CalendarSynchronization {
    private Activity mActivity;
    private boolean mCalendarPermissionsGranted = false;
    public int mCalendarPermissionRequestCode ;
    private String mCalendarReadPermissions;
    private static final String TAG = "CALENDAR_SYNCH";
    // Projection array: columns to return for each row. (Making indices instead of doing dynamic lookups improves performance.)
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };
    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;



    public CalendarSynchronization(String mCalendarReadPermissions, Activity activity, int mCalendarPermissionRequestCode) {
        this.mCalendarReadPermissions = mCalendarReadPermissions;
        this.mActivity = activity;
        this.mCalendarPermissionRequestCode = mCalendarPermissionRequestCode;
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

    public List<CalendarObject> getAllCalendars() {
        getCalendarPermission();
        Cursor calendarCursor = getCalendarCursor();
        return calendarResults(calendarCursor);
    }


    public Cursor getCalendarCursor() {
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selectionClause = CalendarContract.Calendars.VISIBLE + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;
        //onRequestPermissionsResult is overwritten in SettingsPersonalActivity where function is called
        @SuppressLint("MissingPermission") Cursor calendarCursor =
                contentResolver.query(uri, EVENT_PROJECTION, selectionClause, selectionArgs, sortOrder);
        return calendarCursor;
    }

    public List<CalendarObject> calendarResults(Cursor calendarCursor) {
        List<CalendarObject> calendars = new ArrayList<>();
        // Use the cursor to step through the returned records
        while (calendarCursor.moveToNext()) {
            long calendarID=calendarCursor.getLong(PROJECTION_ID_INDEX);
            String displayName = calendarCursor.getString(PROJECTION_DISPLAY_NAME_INDEX);;
            String accountName = calendarCursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = calendarCursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            //add to calendars list
            calendars.add(new CalendarObject(calendarID,displayName,accountName,ownerName));
            // print in log
            Log.d(TAG, "calendarID: " + calendarID + " displayName: " + displayName + " accountName: " + accountName + " ownerName: " + ownerName);
        }
        return calendars;
    }

    public void setCalendarPermissionsGranted(boolean calendarPermissionsGranted) {
        this.mCalendarPermissionsGranted = calendarPermissionsGranted;
    }
}
