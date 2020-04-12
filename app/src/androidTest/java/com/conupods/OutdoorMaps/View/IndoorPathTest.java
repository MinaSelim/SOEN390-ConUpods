package com.conupods.OutdoorMaps.View;

import android.util.Utility;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.conupods.IndoorMaps.IndoorOverlayHandlers.HallBuildingHandler;
import com.conupods.MapsActivity;
import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;
import com.conupods.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IndoorPathTest {
    private final static String TAG = "INDOOR_PATH_TEST";
    UiDevice mDevice = null;
    IndoorOverlayTest HBuildingTest = new IndoorOverlayTest();

    @Rule
    public ActivityTestRule<MapsActivity> mapsActivityRule =
            new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setUpEach() {
        Utility.turnOnDeviceLocation(TAG);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    //TODO: function to check view exists and then click view
    public void performClick(int resource, String text){
        onView(withText(text)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(resource)).perform(click());
    }
    public void executeSearch(int resourceView, int recycler, String text){
        onView(withId(resourceView)).perform(typeText(text));
        onView(withId(recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void startSearchActivityTest() {
        String currentLocation = "H 917";
        String destination = "H 921";

        UiObject searchBar = mDevice.findObject(new UiSelector().resourceId(("android:id/search_bar")));
        Assert.assertTrue("Search bar exists", searchBar.exists());

        executeSearch(R.id.searchBar,R.id.recycler_view, destination);
        //executeSearch(R.id.searchBar,R.id.recycler_view, destination);
//        onView(withId(R.id.searchBar)).perform(typeText(destination));
//        onView(withId(R.id.recycler_view))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        performClick(R.id.get_directions_btn, "Get Directions");
        performClick(R.id.modeSelect_from, "From: Current Location");

//        onView(withText("Get Directions")).check(matches(isDisplayed()));
//        onView(ViewMatchers.withId(R.id.get_directions_btn)).perform(click());

//        onView(withText("From: Current Location")).check(matches(isDisplayed()));
//        onView(ViewMatchers.withId(R.id.modeSelect_from)).perform(click());

        executeSearch(R.id.fromSearchBar,R.id.recycler_view, currentLocation);


//        onView(withId(R.id.fromSearchBar)).perform(typeText(currentLocation));
//        onView(withId(R.id.recycler_view))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
        performClick(R.id.modeSelect_walkingButton, "Walking");
//        onView(withText("Walking")).check(matches(isDisplayed()));
//        onView(ViewMatchers.withId(R.id.modeSelect_walkingButton)).perform(click());

    }
}
