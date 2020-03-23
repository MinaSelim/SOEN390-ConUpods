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
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
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
            Log.w(TAG,"Turn on device location button not found");
        }
    }
}
