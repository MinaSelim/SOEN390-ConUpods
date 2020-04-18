package android.util;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

/**
 * A helper class for reusable test functions.
 */
public class Utility {
    /**
     * A necessary function to automatically grant app location permissions.
     * This function simply clicks the "OK" button when prompted to turn on
     * the device location.
     */
    public static void turnOnDeviceLocation(String TAG) {
        UiDevice device;
        try {
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        } catch (Exception e) {
            Log.e(TAG, "Device not found");
            return;
        }

        // API 23
        try {
            UiObject allowGpsBtn = device.findObject(new UiSelector()
                    .className("android.widget.Button").packageName("com.google.android.gms")
                    .resourceId("android:id/button1")
                    .clickable(true).checkable(false));
            device.pressDelete(); // just in case to turn ON blur screen (not a wake up) for some devices like HTC and some other
            if (allowGpsBtn.exists() && allowGpsBtn.isEnabled()) {
                do {
                    allowGpsBtn.click();
                } while (allowGpsBtn.exists());
            }
        } catch (UiObjectNotFoundException e) {
            Log.w(TAG, "OLD API: Turn on device location button not found");
        }

        // API 29
        try {
            UiObject allowPermissionLocBtn = device.findObject(
                    new UiSelector().className("android.widget.Button")
                            .resourceId("com.android.permissioncontroller:id/permission_allow_foreground_only_button")
                            .clickable(true).checkable(false));

            if (allowPermissionLocBtn.exists() && allowPermissionLocBtn.isEnabled()) {
                do {
                    allowPermissionLocBtn.click();
                } while (allowPermissionLocBtn.exists());
            }
        } catch (UiObjectNotFoundException e) {
            Log.w(TAG, "NEW API: Turn on device app location permission button not found");
        }
    }


    /**
     * A necessary function to automatically grant app calendar permissions.
     * This function simply clicks the "OK" button when prompted to grant access to
     * the device's calendar.
     * IMPORTANT: the device must be set up and signed in to a google account
     */
    public static void turnOnCalendarPermissions(String TAG) {
        UiDevice device;
        try {
            device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        } catch (Exception e) {
            Log.e(TAG, "Device not found");
            return;
        }

        // API
        try {
            UiObject allowPermissionCalBtn = device.findObject(
                    new UiSelector().className("android.widget.Button")
                            .resourceId("com.android.permissioncontroller:id/permission_allow_foreground_only_button")
                            .clickable(true).checkable(false));

            if (allowPermissionCalBtn.exists() && allowPermissionCalBtn.isEnabled()) {
                do {
                    allowPermissionCalBtn.click();
                } while (allowPermissionCalBtn.exists());
            }
        } catch (UiObjectNotFoundException e) {
            Log.w(TAG, "calendar permission button not found");
        }
    }


}
