package com.conupods.Calendar;

import android.util.Utility;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.conupods.OutdoorMaps.View.Settings.SettingsPersonalActivity;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;
/*
NOTE: You must be connected to the Dummy google account for tests to function correctly
 */

@RunWith(AndroidJUnit4.class)
@Ignore
public class CalendarConfigTest {
    private final static String TAG = "TEST_CALENDAR_SETUP";
    // This rule launches the activity containing the view we are testing.
    // In this case the calendar setup is part of settings_page_personal and settings_calendar_popup  which is managed by SettingsPersonalActivity Class

    @Rule
    public ActivityTestRule<SettingsPersonalActivity> activityRule =
            new ActivityTestRule<>(SettingsPersonalActivity.class);

    @Before
    public void setUp() {
        Utility.turnOnDeviceLocation(TAG);
        Utility.turnOnCalendarPermissions(TAG);
    }

    @Test
    public void popUpDisplays(){

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject googleCalendarBttn= device.findObject(new UiSelector().resourceId("com.conupods:id/linkedAccount"));
        UiObject popup;
       // UiObject closeBtn = device.findObject(new UiSelector().resourceId("com.conupods:id/close_popup"));
        try {
            googleCalendarBttn.click();
            UiSelector popUpSelector=new UiSelector().resourceId("com.conupods:id/settings_calendar_view");
            popup = device.findObject(popUpSelector);
            assertTrue("calendar popup  exists", popup.exists());
            UiObject calendarBttn= device.findObject(new UiSelector().text("School Schedule"));
            calendarBttn.click();
        } catch (UiObjectNotFoundException ignore) {
            fail("pop up window not found");
        }
    }


    @Test
    public void selectACalendar(){

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject googleCalendarBttn= device.findObject(new UiSelector().resourceId("com.conupods:id/linkedAccount"));
        UiObject popup;
        // UiObject closeBtn = device.findObject(new UiSelector().resourceId("com.conupods:id/close_popup"));
        try {
            googleCalendarBttn.click();
            UiSelector popUpSelector=new UiSelector().resourceId("com.conupods:id/settings_calendar_view");
            popup = device.findObject(popUpSelector);
            UiObject calendarBttn= device.findObject(new UiSelector().text("School Schedule"));
            calendarBttn.click();
            assertEquals(SettingsPersonalActivity.mSelectedCalendar.getDisplayName(),"School Schedule");

        } catch (UiObjectNotFoundException ignore) {
            fail("selected calendar doesnt match expected");
        }
    }

    @Test
    public void nextEvent(){
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject googleCalendarBttn= device.findObject(new UiSelector().resourceId("com.conupods:id/linkedAccount"));
        UiObject popup;
        try {
            googleCalendarBttn.click();
            UiSelector popUpSelector=new UiSelector().resourceId("com.conupods:id/settings_calendar_view");
            popup = device.findObject(popUpSelector);
            UiObject calendarBttn= device.findObject(new UiSelector().text("School Schedule"));
            calendarBttn.click();
            CalendarObject c =SettingsPersonalActivity.mSelectedCalendar;
            assertEquals("Biology Lecture",c.getmNextEvent().getmNextEventTitle());
            assertEquals("April 17",c.getmNextEvent().getmNextEventDate());
            assertEquals("H-110, ",c.getmNextEvent().getmNextEventLocation());
            assertEquals("14:0-15:0",c.getmNextEvent().getmNextEventStartTime()+"-"+c.getmNextEvent().getmNextEventEndTime());

        } catch (UiObjectNotFoundException ignore) {
            fail("next event doesnt match expected");
        }

    }

}
