package com.conupods.Calendar;

import org.junit.Test;

import com.conupods.Calendar.JuilanDayConverter;

import static org.junit.Assert.assertEquals;

public class CalendarTest {

    @Test
    public void JulianToDayConverterTest(){
        JuilanDayConverter julianDay = new JuilanDayConverter();

        assertEquals("April 11",julianDay.getMonthDayString(2458951));

        JuilanDayConverter julianDay2 = new JuilanDayConverter();
        assertEquals("May 1",julianDay2.getMonthDayString(2458971));
    }


}
