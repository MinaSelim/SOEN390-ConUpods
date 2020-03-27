package com.conupods.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Utility;
import android.view.View;
import android.widget.CheckBox;

import androidx.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.conupods.R;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class SettingsActivityTest {
    private static final String TAG = "SETTINGS_ACTIVITY_TEST";
    @Rule
    public ActivityTestRule<SettingsActivity> mActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);

    @Rule
    public ActivityTestRule<SettingsInfoActivity> mActivityInfoTestRule = new ActivityTestRule<>(SettingsInfoActivity.class);

    @Rule
    public ActivityTestRule<SettingsPersonalActivity> mActivityPersonalTestRule = new ActivityTestRule<>(SettingsPersonalActivity.class);

    @Before
    public void setUp() {
        Utility.turnOnDeviceLocation(TAG);
    }

    @Test
    public void launchSettingsTest() {
        SettingsActivity mActivity = mActivityTestRule.getActivity();
        View settingsView = mActivity.findViewById(R.id.myPreferences);
        assertNotNull(settingsView);
    }

    @Test
    public void launchSettingsInfoTest() {
        SettingsInfoActivity mActivityInfo = mActivityInfoTestRule.getActivity();
        View settingsView = mActivityInfo.findViewById(R.id.contactUs);
        assertNotNull(settingsView);
    }

    @Test
    public void launchSettingsPersonalTest() {
        SettingsPersonalActivity mActivityPersonal = mActivityPersonalTestRule.getActivity();
        View settingsView = mActivityPersonal.findViewById(R.id.googleCalendar);
        assertNotNull(settingsView);
    }

    @Test
    public void goToSettingsPersonalActivityFromSettingsActivityTest() {
        SettingsActivity mActivity = mActivityTestRule.getActivity();
        mActivity.findViewById(R.id.toggle2_1).callOnClick();
        onView(withText("My account")).check(matches(isDisplayed()));
    }

    @Test
    public void goToSettingsInfoActivityFromSettingsActivityTest() {
        SettingsActivity mActivity = mActivityTestRule.getActivity();
        mActivity.findViewById(R.id.toggle2_2).callOnClick();
        onView(withText("Help")).check(matches(isDisplayed()));
    }

    @Test
    public void exitSettingsActivity() {
        SettingsActivity mActivity = mActivityTestRule.getActivity();
        mActivity.findViewById(R.id.done2).callOnClick();
        onView(withText("SGW")).check(matches(isDisplayed()));
    }

    @Test
    public void changePreferencesTest() {
        SettingsActivity mActivity = mActivityTestRule.getActivity();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        CheckBox preference = new CheckBox(mActivity.getApplicationContext());
        preference.setId(0);
        edit.putBoolean(String.valueOf(preference.getId()), true).apply();
        mActivity.changePreferences(preference);
        mActivity.checkBoxIfInPreference(preference);
        assertFalse(preference.isChecked());
    }

}