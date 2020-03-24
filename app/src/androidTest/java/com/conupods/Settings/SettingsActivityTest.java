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

import com.conupods.R;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.*;

public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);
    private SettingsActivity mActivity = null;

    @Rule
    public ActivityTestRule<SettingsInfoActivity> mActivityInfoTestRule = new ActivityTestRule<>(SettingsInfoActivity.class);
    private SettingsInfoActivity mActivityInfo = null;

    @Rule
    public ActivityTestRule<SettingsPersonalActivity> mActivityPersonalTestRule = new ActivityTestRule<>(SettingsPersonalActivity.class);
    private SettingsPersonalActivity mActivityPersonal = null;

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        mActivityInfo = mActivityInfoTestRule.getActivity();
        mActivityPersonal = mActivityPersonalTestRule.getActivity();
    }

    @Test
    public void launchSettingsTest() {
        View settingsView = mActivity.findViewById(R.id.myPreferences);
        assertNotNull(settingsView);
    }

    @Test
    public void launchSettingsInfoTest() {
        View settingsView = mActivityInfo.findViewById(R.id.contactUs);
        assertNotNull(settingsView);
    }

    @Test
    public void launchSettingsPersonalTest() {
        View settingsView = mActivityPersonal.findViewById(R.id.googleCalendar);
        assertNotNull(settingsView);
    }

    @Test
    public void goToSettingsPersonalActivityFromSettingsActivityTest() {
        mActivity.findViewById(R.id.toggle2_1).callOnClick();
        assertTrue(mActivityPersonal.findViewById(R.id.email).isShown());
    }

    @Test
    public void goToSettingsInfoActivityFromSettingsActivityTest() {
        mActivity.findViewById(R.id.toggle2_2).callOnClick();
        assert(mActivityInfo.findViewById(R.id.contactUs).isShown());
    }

    @Test
    public void changePreferencesTest() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        CheckBox preference = mActivity.findViewById(R.id.concordiaShuttle);
        mActivity.changePreferences(preference);
        assertTrue(preferences.getBoolean(String.valueOf(preference.getId()), false));
    }

    @After
    public void tearDown() {
        mActivity = null;
        mActivityInfo = null;
        mActivityPersonal = null;
    }

}