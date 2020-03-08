package com.conupods;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.conupods.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROOR_DIALOG_REQUEST = 9001;
    Handler mLaunchMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLaunchMaps = new Handler();
        mLaunchMaps.postDelayed(() -> {
            if (isGoogleAPIServiceAvailable()) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private boolean isGoogleAPIServiceAvailable() {
        Log.d(TAG, "Checking is Google API services are available...");
        boolean googleAPIServiceAvailable = false;

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int available = googleApiAvailability.isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isGoogleAPIServiceAvailable: Google API Services avvailable");
            googleAPIServiceAvailable = true;
        } else if (googleApiAvailability.isUserResolvableError(available)) {
            Log.d(TAG, "An error occured but can be resolved.");
            Dialog dialog = googleApiAvailability.getErrorDialog(MainActivity.this, available, ERROOR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant make map requests without permissions", Toast.LENGTH_SHORT).show();
        }

        return googleAPIServiceAvailable;
    }
}
