package com.conupods.OutdoorMaps.View.PointsOfInterest;

import android.os.RemoteException;
import android.util.Log;
import android.util.Utility;

import com.conupods.MapsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class POITests {

    private static final String TAG = "POI_TEST";
    UiDevice mDevice = null;

    @Rule
    public ActivityTestRule<MapsActivity> activityRule
            = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setUpEach() {
        Utility.turnOnDeviceLocation(TAG);
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void viewPagerScrollTest() throws RemoteException, UiObjectNotFoundException {
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UiScrollable pager = new UiScrollable(new UiSelector().resourceId("com.conupods:id/POI_ViewPager"));
        pager.flingToEnd(30);


    }


    @Test
    public void getDirectionForViewPagerItemTest() throws RemoteException, UiObjectNotFoundException {
        UiObject mMap = mDevice.findObject(new UiSelector()
                .className("android.view.View")
                .descriptionContains("Map"));
        assertTrue("Map exits", mMap.exists());


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UiScrollable pager = new UiScrollable(new UiSelector().resourceId("com.conupods:id/POI_ViewPager"));
        pager.clickAndWaitForNewWindow(2000);

    }
}
