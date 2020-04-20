package com.conupods.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JuilanDayConverter {

    private int mJulianDay; //julian day
    private int mDay;
    private int mMonth;


    /*
    Fliegel-Van Flandern algorithm for converting from a Julian date to a Gregorian date
     */
    private void juilanToDate() {
        //manipulation variables
        int p, q, r, s, t, u, v;
        //Fliegel-Van Flandern's algorithm constants and manipulation variables
        final int c1=68569;
        final int c2=4;
        final int c3=146097;
        final int c4=3;
        final int c5 = 4000;
        final int c6 = 1;
        final int c7 = 1461001;
        final int c8 = 1461;
        final int c9 = 31;
        final int c10 = 80;
        final int c11 = 2447;
        final int c12 =100;
        final int c13 =49;
        final int c14 =2;
        final int c15 =12;

        p = mJulianDay + c1;
        q = c2 * p / c3;
        r = p - (c3 * q + c4) / c2;
        s = c5 * (r + c6) / c7;
        t = r - c8 * s / c2 + c9;
        u = c10 * t / c11;
        v = u / c12;

        mMonth = u + c14 - c15 * v;
        mDay = t - c11 * u / c10;
    }

    /*
    returns the date in the string format "April 12"
     */
    public String getMonthDayString(int jd) {
        this.mJulianDay = jd;
        juilanToDate();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat monthDate = new SimpleDateFormat("MMMM d");
        /*minus 1 since the The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0;
        the last depends on the number of months in a year.*/
        cal.set(Calendar.MONTH, mMonth - 1);
        cal.set(Calendar.DAY_OF_MONTH, mDay);
        return monthDate.format(cal.getTime());
    }


}
