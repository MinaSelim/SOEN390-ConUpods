package com.conupods;

import com.conupods.OutdoorMaps.View.MainActivity;
import com.conupods.OutdoorMaps.View.MapsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class navigateActivitiesInstrumentedTest {


    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void pressMapsViewButton() {

        //Navigation towards the correct view
        onView(withId(R.id.MapActivityButton)).perform(click());

        //Verify that the activity has now changed
        intended(hasComponent(MapsActivity.class.getName()));


    }

}