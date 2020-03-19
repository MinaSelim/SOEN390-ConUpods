package com.conupods.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.conupods.OutdoorMaps.View.MapsActivity;
import com.conupods.R;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);
    private SettingsActivity mActivity = null;

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View settingsView = mActivity.findViewById(R.id.transitOptions);
        assertNotNull(settingsView);
    }

    @Test
    public void launchSettingsPersonalActivity() {

        //Navigation towards the correct view
        onView(withId(R.id.toggle2_1)).perform(click());

        //Verify that the activity has now changed
        intended(hasComponent(SettingsPersonalActivity.class.getName()));
    }

    @Test
    public void launchSettingsInfoActivity() {

        //Navigation towards the correct view
        onView(withId(R.id.toggle2_2)).perform(click());

        //Verify that the activity has now changed
        intended(hasComponent(SettingsInfoActivity.class.getName()));
    }

    @Test
    public void launchMapsActivity() {

        //Navigation towards the correct view
        onView(withId(R.id.done2)).perform(click());

        //Verify that the activity has now changed
        intended(hasComponent(MapsActivity.class.getName()));
    }

    @Test
    public void changePreferencesTest() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        CheckBox preference = mActivity.findViewById(R.id.metro);
        mActivity.changePreferences(preference);
        assertTrue(preferences.getBoolean(String.valueOf(preference.getId()), false));
    }

    @After
    public void tearDown() {
        mActivity = null;
    }

}