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
    public int mCalendarPermissionRequestCode;
    private String mCalendarReadPermissions;
    private Cursor mCalendarCursor;

    private static final String TAG = "CALENDAR_SYNCH";

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
    //returns a list of all calendars visible on the user's account
    public List<CalendarObject> getAllCalendars() {
        List<CalendarObject> calendarList;
        if (!mCalendarPermissionsGranted) {
            getCalendarPermission();
        }

        initCalendarCursor();
        calendarList = getCalendarList(mCalendarCursor);
        mCalendarCursor.close();
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


    //onRequestPermissionsResult is overwritten in SettingsPersonalActivity
    @SuppressLint("MissingPermission")
    private void initCalendarCursor() {
        // Projection array: columns to return for each row. (Making indices instead of doing dynamic lookups improves performance.)
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
        };
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selectionClause = CalendarContract.Calendars.VISIBLE + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;
        mCalendarCursor = contentResolver.query(uri, projection, selectionClause, selectionArgs, sortOrder);
    }

    private List<CalendarObject> getCalendarList(Cursor calendarCursor) {
        List<CalendarObject> calendars = new ArrayList<>();
        // Use the cursor to step through the returned records
        while (calendarCursor.moveToNext()) {
            String calendarID = calendarCursor.getString(PROJECTION_ID_INDEX);
            String displayName = calendarCursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            String accountName = calendarCursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = calendarCursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            //add to calendars list
            calendars.add(new CalendarObject(calendarID, displayName, accountName, ownerName));
            // print in log
            Log.d(TAG, "calendarID: " + calendarID + " displayName: " + displayName + " accountName: " + accountName + " ownerName: " + ownerName);
        }
        return calendars;
    }

    public void setCalendarPermissionsGranted(boolean calendarPermissionsGranted) {
        this.mCalendarPermissionsGranted = calendarPermissionsGranted;
    }
}
