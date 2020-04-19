package com.conupods.Calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.conupods.App;

import java.util.Calendar;

public class Event {
    private static final String TAG = "EVENT";

    private String mCalendarID;
    // next upcoming event data
    private String mNextEventTitle;
    private String mNextEventLocation;
    private String mNextEventStartTime;
    private String mNextEventEndTime;
    private String mNextEventDate;

    private Cursor mEventCursor;

    // indexes for query result (projection array)
    private static final int PROJECTION_TITLE_INDEX = 0;
    private static final int PROJECTION_START_DAY_INDEX = 1;
    private static final int PROJECTION_START_MINUTE_INDEX = 2;
    private static final int PROJECTION_END_MINUTE_INDEX = 3;
    private static final int PROJECTION_EVENT_LOCATION_INDEX = 4;
    private int mStartMinute=0;

    public Event(String mCalendarID) {
        this.mCalendarID = mCalendarID;
    }

    /*
           initializes instance variables
           returns true if there is a next event and false otherwise
            */
    public Boolean loadNextEvent() {
        boolean hasNextEvent;
        initEventCursor();

        if (mEventCursor != null && mEventCursor.moveToNext()) {
            mNextEventTitle = mEventCursor.getString(PROJECTION_TITLE_INDEX);
            int startDay = mEventCursor.getInt(PROJECTION_START_DAY_INDEX);
            mStartMinute = mEventCursor.getInt(PROJECTION_START_MINUTE_INDEX);
            int endMinute = mEventCursor.getInt(PROJECTION_END_MINUTE_INDEX);
            mNextEventLocation = mEventCursor.getString(PROJECTION_EVENT_LOCATION_INDEX);
            mNextEventStartTime = minutesToHours(mStartMinute) + ":" + minutesToMin(mStartMinute);
            mNextEventEndTime = minutesToHours(endMinute) + ":" + minutesToMin(endMinute);
            mNextEventDate = new JuilanDayConverter().getMonthDayString(startDay);
            hasNextEvent = true;
            Log.d(TAG, "Event Name: " + mNextEventTitle + " startDay : " + startDay + " startMinute: " + mStartMinute + "event time: " + mNextEventStartTime + "-" + mNextEventEndTime + "event date: " + mNextEventDate + " eventLocation: " + mNextEventLocation);
        } else {
            hasNextEvent = false;
            Log.d(TAG, "no upcomming events");
        }
        mEventCursor.close();
        //TODO: remove after testing
        upcomingEventSoon();
        return hasNextEvent;
    }


    private void initEventCursor() {
        ContentResolver contentResolver = App.getContext().getContentResolver();
        //set time frame of 24h for query
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE));

        long startMills = rightNow.getTimeInMillis();
        final long oneDayInMillis = 86400000;
        long endMills = rightNow.getTimeInMillis() + oneDayInMillis;

        //add the start-end time to the Content URIs
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMills);
        ContentUris.appendId(builder, endMills);

        //define query params
        Uri uri = builder.build();
        String[] projection = new String[]{
                CalendarContract.Events.TITLE,              //0
                CalendarContract.Instances.START_DAY,       //1
                CalendarContract.Instances.START_MINUTE,    //2
                CalendarContract.Instances.END_MINUTE,      //3
                CalendarContract.Events.EVENT_LOCATION      //4
        };
        String selectionClause = CalendarContract.Instances.CALENDAR_ID + " = ?";
        String[] selectionArgs = new String[]{mCalendarID};
        String sortOrder = CalendarContract.Events.DTSTART + " ASC";

        //initialize cursor with query
        mEventCursor = contentResolver.query(uri, projection, selectionClause, selectionArgs, sortOrder);
    }

    /*
    returns true if the next event is occurring in 60m or less
     */
    public boolean upcomingEventSoon() {
        Calendar now = Calendar.getInstance();
        int nowMinute;
        int minuteInHour = now.MINUTE;
        int hourInMinute = now.HOUR * 60;

        if(now.get(Calendar.AM_PM)==1){ hourInMinute= hourInMinute+ (12*60); }

        nowMinute = hourInMinute + minuteInHour;

        if (mStartMinute>0 && ((mStartMinute - nowMinute)<=60)) { return true; }

        return false;
    }


    public int minutesToHours(int allMinutes) {
        return allMinutes / 60;
    }

    public String minutesToMin(int allMinutes) {
        int minutes = allMinutes % 60;
        if (minutes < 10) {
            return "0" + minutes;
        } else {
            return "" + minutes;
        }
    }

    public String getmNextEventTitle() {
        return mNextEventTitle;
    }

    public String getmNextEventLocation() {
        return mNextEventLocation;
    }

    public String getmNextEventStartTime() {
        return mNextEventStartTime;
    }

    public String getmNextEventEndTime() {
        return mNextEventEndTime;
    }

    public String getmNextEventDate() {
        return mNextEventDate;
    }
}
