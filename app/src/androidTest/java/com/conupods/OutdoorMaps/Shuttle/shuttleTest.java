package com.conupods.OutdoorMaps.Shuttle;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.conupods.OutdoorMaps.Services.Shuttle;

public class shuttleTest {

    private String[] LOYscheduleStandardTest;
    private String[] LOYscheduleFridayTest;
    private String[] SGWscheduleStandardTest;
    private String[] SGWscheduleFridayTest;
    private String[] LOYscheduleStandard;
    private String[] LOYscheduleFriday;
    private String[] SGWscheduleStandard;
    private String[] SGWscheduleFriday;

    @Test
    public void shuttleLoyolaStandardTest(){

        LOYscheduleStandardTest = new String[] {
                "07:10", "07:35", "07:45", "08:15", "08:25", "08:40", "09:05",
                "09:25", "09:35", "10:15", "10:25", "10:40", "11:00", "11:15",

                "11:55", "12:25", "12:55", "13:25", "13:55", "14:25", "14:55", "15:25",
                "15:55", "16:25", "16:55", "17:25", "17:55", "18:25", "19:25", "19:55",

                "20:25", "20:45", "20:55", "21:25", "21:55", "22:25", "22:55"
        };

        LOYscheduleStandard = new String[] {
                "07:30", "07:40", "07:55", "08:20", "08:35", "08:55", "09:10",
                "09:30", "09:45", "10:20", "10:35", "10:55", "11:10", "11:30",

                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:30", "20:00",

                "20:30", "20:50", "21:05", "21:30", "22:00", "22:30", "23:00"
        };

        Shuttle shuttle = new Shuttle();

        for(int i = 0;i<LOYscheduleStandardTest.length; i++)
        assertEquals(LOYscheduleStandard[i],
                shuttle.getNextShuttle("LOY", true, LOYscheduleStandardTest[i]));
    }

    @Test
    public void shuttleLoyolaFridayTest(){

        LOYscheduleFridayTest = new String[] {
                "07:30", "07:45", "08:30", "09:00", "10:00", "10:25", "11:00",
                "11:25", "12:00", "12:30", "13:15", "13:40", "14:15", "15:00",
                "15:20", "16:00", "16:30", "16:45", "17:30", "18:25", "18:55", "19:25",
        };

        LOYscheduleFriday = new String[] {
                "07:40", "08:15", "08:55", "09:30", "10:20", "10:35", "11:10",
                "11:45", "12:20", "12:55", "13:30", "14:05", "14:40", "15:15",
                "15:50", "16:25", "16:40", "17:00", "18:05", "18:40", "19:15",
                "19:50"
        };

        Shuttle shuttle = new Shuttle();

        for(int i = 0;i<LOYscheduleFridayTest.length; i++)
            assertEquals(LOYscheduleFriday[i],
                    shuttle.getNextShuttle("LOY", false, LOYscheduleFridayTest[i]));
    }

    @Test
    public void shuttleSGWStandardTest(){

        SGWscheduleStandardTest = new String[] {
                "07:10", "07:50", "08:10", "08:25", "08:45", "09:00", "09:15",
                "09:35", "09:55", "10:15", "10:25", "11:00", "11:15",

                "11:55", "12:25", "12:55", "13:25", "13:55", "14:25", "14:55", "15:25",
                "15:55", "16:25", "16:55", "17:25", "17:55", "18:25", "19:25", "19:55",

                "20:00", "20:25", "20:55", "21:15", "21:35", "21:55", "22:25","22:45"
        };

        SGWscheduleStandard = new String[] {
                "07:45", "08:05", "08:20", "08:35", "08:55", "09:10", "09:30",
                "09:45", "10:05", "10:20", "10:55", "11:10", "11:45",

                "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:30", "20:00",

                "20:15", "20:30", "21:00", "21:25", "21:45", "22:05", "22:30",
                "23:00"
        };

        Shuttle shuttle = new Shuttle();

        for(int i = 0;i<SGWscheduleStandardTest.length; i++)
            assertEquals(SGWscheduleStandard[i],
                    shuttle.getNextShuttle("SGW", true, SGWscheduleStandardTest[i]));
    }

    @Test
    public void shuttleSGWFridayTest(){

        SGWscheduleFridayTest = new String[] {
                "07:30", "07:55", "08:30", "09:00", "09:45", "10:25", "11:00",
                "11:25", "12:00", "12:30", "13:15", "13:40", "14:15", "15:00",
                "15:20", "16:00", "16:30", "17:20", "17:35", "18:25", "18:55",
                "19:25",
        };

        SGWscheduleFriday = new String[] {
                "07:45", "08:20", "08:55", "09:30", "10:05", "10:55", "11:10",
                "11:45", "12:20", "12:55", "13:30", "14:05", "14:40", "15:15",
                "15:50", "16:25", "17:15", "17:30", "18:05", "18:40", "19:15",
                "19:50"
        };

        Shuttle shuttle = new Shuttle();

        for(int i = 0;i<SGWscheduleFridayTest.length; i++)
            assertEquals(SGWscheduleFriday[i],
                    shuttle.getNextShuttle("SGW", false, SGWscheduleFridayTest[i]));
    }
}

