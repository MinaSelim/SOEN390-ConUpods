package com.conupods;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import androidx.test.espresso.Espresso.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.rule.ActivityTestRule;


@RunWith(AndroidJUnit4.class)
public class BuildingInfoWindowTest {

    @Rule
    public ActivityTestRule<MapsActivity> activityRule
            = new ActivityTestRule<>(MapsActivity.class);

    @Test
    public void BuildingInfoWindowTest() { }

}
