package com.conupods.Calendar;

public class CalendarObject {
    private long calendarID;
    private String displayName ;
    private String accountName ;
    private String ownerName ;

    public CalendarObject(long calendarID,String displayName, String accountName, String ownerName) {
        this.calendarID= calendarID;
        this.displayName = displayName;
        this.accountName = accountName;
        this.ownerName = ownerName;
    }

    public long getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(long calendarID) {
        this.calendarID = calendarID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
