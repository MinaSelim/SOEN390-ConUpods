package com.conupods;

import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import androidx.test.espresso.action.ViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class currentLocationInstrumentedTest {

    private String stringToBetyped;

    @Before
    public void initString() {
        stringToBetyped = "Location Desired";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.search))
                .perform(typeText(stringToBetyped), closeSoftKeyboard())
                .perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));


        // Check that the text was changed.
        onView(withId(R.id.search))
                .check(matches(withText(stringToBetyped)));
    }
}
