package com.conupods.OutdoorMaps.View;

import android.util.Utility;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {
    private final static String TAG = "MAIN_ACTIVITY_TEST";
    private MainActivity mainActivity = null;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Utility.turnOnDeviceLocation(TAG);
    }

    @Test
    public void startMainActivityTest() {
        mainActivity = activityRule.getActivity();
        assertNotNull("Main activity exists", mainActivity);
    }

}
