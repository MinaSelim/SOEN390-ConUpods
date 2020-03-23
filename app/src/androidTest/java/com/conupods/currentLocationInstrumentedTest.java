package com.conupods;

import android.util.Utility;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.conupods.OutdoorMaps.View.MapsActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class currentLocationInstrumentedTest {
    private final static String TAG = "TEST_SEARCH";
    // This rule launches the activity containing the view we are testing.
    // In this case the search is part of activity_maps which is managed by MapsActivity Class
    @Rule
    public ActivityTestRule<MapsActivity> activityRule =
            new ActivityTestRule<>(MapsActivity.class);

    private String stringToBetyped;

    @Before
    public void initString() {
        stringToBetyped = "Location Desired";
        Utility.turnOnDeviceLocation(TAG);
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.search))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        // TODO: search bar does not yet support enter event
        //.perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))

        // Verify the text was succesfully typed.
        onView(withId(R.id.search))
                .check(matches(withText(stringToBetyped)));
    }
}
