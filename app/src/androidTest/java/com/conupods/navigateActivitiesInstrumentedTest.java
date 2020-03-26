package com.conupods;

import android.util.Utility;

import com.conupods.OutdoorMaps.View.MapsActivity;

import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import junit.framework.AssertionFailedError;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class navigateActivitiesInstrumentedTest {
    private static final String TAG = "TEST_NAVIGATION";
    private static final String[] SGW_CODES = {"Z", "MM", "Q", "LC", "GNL", "K", "EN", "MK", "CI", "V", "FG", "ER", "MI", "LD", "R", "GA", "PR", "MB", "X", "P", "GS", "TU", "S", "ET", "EV", "GM", "B", "VA", "TD", "RR", "T", "MU", "ES", "MT", "FA", "H", "D", "LB", "CL", "MN", "M", "FB"};
    private static final String[] LOY_CODES = {"JR", "PS", "PT", "TA", "CJS", "SC", "HC", "GE", "BB", "RF", "HA", "HB", "HU", "BH", "TB", "RA", "FC", "PC", "CC", "PY", "VL", "SP", "SH", "PB", "AD"};
    private UiDevice mDevice;
    @Rule
    public ActivityTestRule<MapsActivity> activityRule
            = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setUpEach() {
        Utility.turnOnDeviceLocation(TAG);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void navigateSGW() {

        try {
            onView(withText("SGW")).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            fail("SGW button is not displayed");
        }

        onView(withId(R.id.SGW)).perform(click()).check(matches(isDisplayed()));

        // Check that the view contains the markerS for SGW campus.
        for (String sgwCode : SGW_CODES) {
            UiObject marker = mDevice.findObject(
                    new UiSelector().
                            className("android.view.View")
                            .descriptionContains(sgwCode)
            );
            assertTrue("Marker " + sgwCode + " exists", marker.exists());
        }
    }

    @Test
    public void navigateLOY() {
        try {
            onView(withText("LOY")).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            fail("LOY button is not displayed");
        }

        onView(withId(R.id.LOY)).perform(click()).check(matches(isDisplayed()));

        for (String loyCode : LOY_CODES) {
            UiObject marker = mDevice.findObject(
                    new UiSelector()
                            .className("android.view.View")
                            .descriptionContains(loyCode)
            );
            assertTrue("Marker  " + loyCode + " exists", marker.exists());
        }
    }

    @Test
    public void markerClickTest() {
        UiObject marker = mDevice.findObject(
                new UiSelector()
                        .className("android.view.View")
                        .descriptionContains("FB")
        );
        assertTrue("Marker exists", marker.exists());
        try {
            assertTrue("Marker is clickable", marker.click());
        } catch (UiObjectNotFoundException ignored) {
            fail("Marker not found - click test failed");
        }
    }

    @Test
    public void goToCurrentLocation() {
        onView(withText("LOY")).check(matches(isDisplayed()));
        UiObject locationButton = mDevice.findObject(new UiSelector().className("android.widget.Button").resourceId("com.conupods:id/locationButton"));
        assertTrue("Current location button exists", locationButton.exists());

        try {
            assertTrue("Current location button is clickable", locationButton.isClickable());
            locationButton.click();
        } catch (UiObjectNotFoundException ignored) {
            fail("Current location button not found");
        }
    }

}