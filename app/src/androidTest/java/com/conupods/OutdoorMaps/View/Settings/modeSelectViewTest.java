package com.conupods.OutdoorMaps.View.Settings;

import com.conupods.OutdoorMaps.View.Directions.ModeSelectActivity;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static junit.framework.TestCase.assertNotNull;

public class modeSelectViewTest {

    @Rule
    public ActivityTestRule<ModeSelectActivity> modeSelectActivityRule =
            new ActivityTestRule<>(ModeSelectActivity.class);

    @Ignore
    public void uiObjectVerificationsTest() {
        ModeSelectActivity modeSelectActivity = modeSelectActivityRule.getActivity();
        assertNotNull("Main activity exists", modeSelectActivity);
    }
}
