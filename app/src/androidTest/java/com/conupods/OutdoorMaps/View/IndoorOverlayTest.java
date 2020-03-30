package com.conupods.OutdoorMaps.View;

import android.util.Utility;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.conupods.MapsActivity;
import com.conupods.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class IndoorOverlayTest {
    private static final String TAG = "ZOOM_TEST";
    UiDevice mDevice = null;

    @Rule
    public ActivityTestRule<MapsActivity> activityRule
            = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setUpEach() {
        Utility.turnOnDeviceLocation(TAG);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public void hBuildingOverlay() {

        //check if SGW toggle button is displayed
        //click SGW toggle button to ensure map is on the right campus
        onView(withText("SGW")).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.SGW)).perform(click()).check(matches(isDisplayed()));

        //H marker on the map
        UiObject mHBuilding = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("H"));
        assertTrue("H building marker exists", mHBuilding.exists());

        try {
            assertTrue("H marker is clickable", mHBuilding.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("H building marker not found");
        }

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        try {
            assertTrue("Capable of zooming into map", mMap.pinchOut(100, 100));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to zoom into map");
        }

        //Attain hall level buttons
        UiObject floorButtonsH = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/floorButtonsHall"));
        //Attain specific floor level button to test overlay changes on click
        UiObject ninthFloor = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/hall9"));
        floorButtonsH.waitForExists(5000);
        assertTrue("H building floor buttons exist", floorButtonsH.exists());
        assertTrue("H building 9th floor button exists", ninthFloor.exists());

        try {
            assertTrue("Hall 9th floor button is clickable", ninthFloor.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to click on Hall 9th floor button");
        }
    }

    @Test
    public void moveCameraOutOfHBuildingBounds() {
        hBuildingOverlay();

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        try {
            assertTrue("Capable of swiping left on the map", mMap.swipeLeft(70));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to swipe left on the map");
        }

        mMap.waitForExists(5000);
        UiObject floorButtonsH = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/floorButtonsHall"));
        Assert.assertFalse("H building floor buttons don't exist", floorButtonsH.exists());
    }

    @Test
    public void zoomOutTest() throws InterruptedException {

        hBuildingOverlay();

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        try {
            assertTrue("Capable of zooming out of map", mMap.pinchIn(100, 50));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to zoom out of map");
        }

        mMap.waitForExists(5000);
        Thread.sleep(1000);
        UiObject floorButtonsH = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/floorButtonsHall"));
        Assert.assertFalse("H building floor buttons don't exist", floorButtonsH.exists());
    }

    @Test
    public void mbBuildingOverlayTest() {

        //check if SGW toggle button is displayed
        //click SGW toggle button to ensure map is on the right campus
        onView(withText("SGW")).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.SGW)).perform(click()).check(matches(isDisplayed()));

        //MB marker on the map
        UiObject mMBBuilding = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("MB"));
        assertTrue("MB building marker exists", mMBBuilding.exists());

        try {
            assertTrue("MB marker is clickable", mMBBuilding.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("MB building marker not found");
        }

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        try {
            assertTrue("Capable of zooming into map", mMap.pinchOut(100, 100));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to zoom into map");
        }

        //Attain mb level buttons
        UiObject floorButtonsMB = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/floorButtonsMB"));
        //Attain specific floor level button to test overlay changes on click
        UiObject s2Floor = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/MBS2"));
        floorButtonsMB.waitForExists(5000);
        assertTrue("MB building floor buttons exist", floorButtonsMB.exists());
        assertTrue("MB building S2 floor button exists", s2Floor.exists());

        try {
            assertTrue("MB S2 floor button is clickable", s2Floor.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to click on MB S2 floor button");
        }
    }

    @Test
    public void ccBuildingOverlayTest() {

        //check if LOY toggle button is displayed
        //click LOY toggle button to ensure map is on the right campus
        onView(withText("LOY")).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.LOY)).perform(click()).check(matches(isDisplayed()));

        //CC marker on the map
        UiObject mCCBuilding = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("CC"));
        mCCBuilding.waitForExists(5000);
        assertTrue("CC building marker exists", mCCBuilding.exists());

        try {
            assertTrue("CC marker is clickable", mCCBuilding.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("CC building marker not found");
        }

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        //There are no floor buttons for CC, so CC-1 floor map should display
        try {
            assertTrue("Capable of zooming into map", mMap.pinchOut(100, 100));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to zoom into map");
        }
        mMap.waitForExists(5000);

    }

    @Test
    public void vlBuildingOverlayTest() {

        //check if LOY toggle button is displayed
        //click LOY toggle button to ensure map is on the right campus
        onView(withText("LOY")).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.LOY)).perform(click()).check(matches(isDisplayed()));

        //VL marker on the map
        UiObject mVLBuilding = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("VL"));
        mVLBuilding.waitForExists(5000);
        assertTrue("VL building marker exists", mVLBuilding.exists());

        try {
            assertTrue("VL marker is clickable", mVLBuilding.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("VL building marker not found");
        }

        //Attain map object
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());

        try {
            assertTrue("Capable of zooming into map", mMap.pinchOut(100, 100));
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to zoom into map");
        }

        //Attain vl level buttons
        UiObject floorButtonsVL = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/floorButtonsLOYVL"));
        //Attain specific floor level button to test overlay changes on click
        UiObject secondFloor = mDevice.findObject(new UiSelector().resourceId("com.conupods:id/loy_vl2"));
        floorButtonsVL.waitForExists(5000);
        assertTrue("VL building floor buttons exist", floorButtonsVL.exists());
        assertTrue("VL building second floor button exists", secondFloor.exists());

        try {
            assertTrue("VL second floor button is clickable", secondFloor.click());
        } catch (UiObjectNotFoundException ignore) {
            fail("Unable to click on VL second floor button");
        }
    }
}
