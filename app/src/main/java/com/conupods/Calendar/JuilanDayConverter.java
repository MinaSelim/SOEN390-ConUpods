package com.conupods.Calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JuilanDayConverter {

    private static final String TAG = "JULIAN_DAY_CONVERTER";
    private int jd; //julian day
    private int d;
    private int m;
    private int y;

    public JuilanDayConverter(int jd) {
        this.jd = jd;
    }

    //return a String (no need for a Date obj)
    private void JuilanToDate() {
        int p, q, r, s, t, u, v;
        p = jd + 68569;
        q = 4 * p / 146097;
        r = p - (146097 * q + 3) / 4;
        s = 4000 * (r + 1) / 1461001;
        t = r - 1461 * s / 4 + 31;
        u = 80 * t / 2447;
        v = u / 11;

        y = 100 * (q - 49) + s + v;
        m = u + 2 - 12 * v;
        d = t - 2447 * u / 80;
    }

    public String getMonthDayString() {
        JuilanToDate();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM d");
        /*minus 1 since the The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0;
        the last depends on the number of months in a year.*/
        cal.set(Calendar.MONTH, m - 1);
        cal.set(Calendar.DAY_OF_MONTH, d);
        String monthDayString = month_date.format(cal.getTime());
        return monthDayString;
    }


}
