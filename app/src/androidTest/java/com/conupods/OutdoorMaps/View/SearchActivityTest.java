package com.conupods.OutdoorMaps.View;

import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;

public class SearchActivityTest {
    private final static String TAG = "SEARCH_ACTIVITY_TEST";
    private UiDevice mDevice =  UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    // private ModeSelect modeSelectActivity = null;

    @Before
    public void setUp() {

    }

    @Rule
    public ActivityTestRule<MapsActivity> mapsActivityRule =
            new ActivityTestRule<>(MapsActivity.class);
    @Rule
    public ActivityTestRule<SearchActivity> searchActivityRule =
            new ActivityTestRule<>(SearchActivity.class);

    // TODO, When ModeSelect View is pushed, uncomment to test transition from searchView
//  @Rule
//    public ActivityTestRule<ModeSelectActivity> modeSelectActivityRule =
//            new ActivityTestRule<>(ModeSelectActivity.class);

    @Test
    public void startSearchActivityTest() {
        mapsActivityRule.getActivity();
        UiObject searchBar = mDevice.findObject(new UiSelector().className("android.widget.LinearLayout").resourceId("android:id/search_bar"));
        Assert.assertTrue("Search bar exists", searchBar.exists());
        try {
            searchBar.click();
        } catch (UiObjectNotFoundException ignore) {
            fail("Search bar not found on default view");
        }
    }

    @Test
    public void selectDestinationOptionTest() {
        searchActivityRule.getActivity();
        UiObject locationItem = mDevice.findObject(new UiSelector().resourceId(("android:id/search_bar")));
        Assert.assertTrue("Location item exists", locationItem.exists());

        try {
            locationItem.click();
            locationItem.setText("Searching for");
        } catch (UiObjectNotFoundException ignore) {
            fail("Search bar not found on search view");
        }
        UiObject selectLocationItem = mDevice.findObject(new UiSelector().resourceId(("com.conupods:id/recycler_view")));
        Assert.assertTrue("Location item exists", selectLocationItem.exists());
        try {
            selectLocationItem.click();
        } catch (UiObjectNotFoundException ignore) {
            fail("Search bar not found");
        }
    }


    @Ignore

    public void transitionToModeSelectView() {

//        modeSelectActivity = modeSelectActivityRule.getActivity();
//        assertNotNull("Main activity exists", modeSelectActivity);
    }

}