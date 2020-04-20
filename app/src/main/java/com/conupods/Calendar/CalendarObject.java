package com.conupods.Calendar;

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

    public Event getNextEvent() { return mNextEvent; }

    public String getCalendarID() { return mCalendarID; }
}
