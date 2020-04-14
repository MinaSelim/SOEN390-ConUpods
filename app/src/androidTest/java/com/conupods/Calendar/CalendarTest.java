package com.conupods.Calendar;

import org.junit.Test;

import com.conupods.Calendar.JuilanDayConverter;

import static org.junit.Assert.assertEquals;

public class CalendarTest {

    @Test
    public void JulianToDayConverterTest(){
        JuilanDayConverter julianDay = new JuilanDayConverter(2458951);

        assertEquals("April 11",julianDay.getMonthDayString());

        JuilanDayConverter julianDay2 = new JuilanDayConverter(2458971);
        assertEquals("May 1",julianDay2.getMonthDayString());
    }


}
