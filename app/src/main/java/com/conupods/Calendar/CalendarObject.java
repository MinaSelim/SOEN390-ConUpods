package com.conupods.Calendar;

import java.time.temporal.JulianFields;
import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.conupods.App;

/*
Represents a single user calendar.
 */
public class CalendarObject {

    private static final String TAG = "CALENDAR_OBJECT";
    //calendar identifying data
    private String mCalendarID;
    private String mDisplayName;
    private Event mNextEvent;

    public CalendarObject(String calendarID, String displayName) {
        this.mCalendarID = calendarID;
        this.mDisplayName = displayName;
        mNextEvent = new Event(mCalendarID);
    }

    public String getDisplayName() { return mDisplayName; }

    public Boolean hasNextEvent() { return mNextEvent.loadNextEvent(); }

    public Event getmNextEvent() { return mNextEvent; }

    public String getmCalendarID() { return mCalendarID; }
}
