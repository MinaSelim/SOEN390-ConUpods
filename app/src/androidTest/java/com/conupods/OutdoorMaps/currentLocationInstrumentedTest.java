package com.conupods.OutdoorMaps;

import android.util.Utility;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.conupods.MapsActivity;
import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class currentLocationInstrumentedTest {
    private final static String TAG = "TEST_SEARCH";
    // This rule launches the activity containing the view we are testing.
    // In this case the search is part of activity_maps which is managed by MapsActivity Class
    @Rule
    public ActivityTestRule<MapsActivity> activityRule =
            new ActivityTestRule<>(MapsActivity.class);

    public ActivityTestRule<SearchActivity> searchActivity = new ActivityTestRule<>(SearchActivity.class);

    private String stringToBetyped;

    @Before
    public void setUp() {
        stringToBetyped = "Location Desired";
        Utility.turnOnDeviceLocation(TAG);
    }

    @Test
    public void changeText_sameActivity() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiObject searchBar = device.findObject(new UiSelector().resourceId("com.conupods:id/searchBar"));
        Assert.assertTrue("Search bar exists", searchBar.exists());
        try {
            searchBar.click();
        } catch (UiObjectNotFoundException ignore) {
            fail("Search bar not found");
        }

        /**    UiObject searchBarType = device.findObject(new UiSelector().resourceId("com.conupods:id/searchBar"));
         Assert.assertTrue("Search bar exists", searchBarType.exists());
         try {
         searchBarType.setText(stringToBetyped);
         } catch (UiObjectNotFoundException ignore) {
         fail("Search bar not found");
         }*/
    }
}