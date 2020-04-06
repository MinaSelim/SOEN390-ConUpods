package com.conupods;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.BuildingInfoWindow;
import com.conupods.OutdoorMaps.CameraController;
import com.conupods.OutdoorMaps.MapInitializer;
import com.conupods.OutdoorMaps.OutdoorBuildingOverlays;
import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final Logger LOGGER = Logger.getLogger(MapsActivity.class.getName());

    private static final String TAG = "MapsActivity";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final int CALENDAR_PERMISSION_REQUEST_CODE = 1235;
    public static final int RESOLVABLE_API_ERROR_REQUEST_CODE = 51;

    private CameraController mCameraController;
    private SearchView mSearchBar;

    private static final String COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CALENDAR_READ_PERMISSION = Manifest.permission.READ_CALENDAR;

    //Variables for logic
    private boolean mLocationPermissionsGranted = false;
    private boolean mCalendarPermissionsGranted = false;
    //------------temp calendar tryout------------
    // Projection array: columns to return for each row. Creating indices for this array instead of doing dynamic lookups to improve performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };
    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    //-------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initializeMap();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        if (!mLocationPermissionsGranted)
            getLocationPermission();
        else {
            mCameraController.goToDeviceCurrentLocation();
        }
        if (!mCalendarPermissionsGranted) {
            getCalendarPermission();
            Log.d(TAG, "Calling calendarTryout from onCreate() 1");
            calendarTryout();
        } else {
            Log.d(TAG, "Calling calendarTryout from onCreate() 2");
            calendarTryout();
        }
    }

    private void initializeMap() {
        Log.d(TAG, "Initializing Map...");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(TAG, "Map is ready");

        OutdoorBuildingOverlays outdoorBuildingOverlays = new OutdoorBuildingOverlays(map, getString(R.string.geojson_url));
        FusedLocationProviderClient fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        mCameraController = new CameraController(map, mLocationPermissionsGranted, fusedLocationProvider);
        BuildingInfoWindow buildingInfoWindow = new BuildingInfoWindow(getLayoutInflater());

        if (mLocationPermissionsGranted) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            createLocationRequest();
        }

        IndoorBuildingOverlays indoorBuildingOverlays = new IndoorBuildingOverlays(findViewById(R.id.floorButtonsGroup), map);
        MapInitializer mapInitializer = new MapInitializer(mCameraController, indoorBuildingOverlays, outdoorBuildingOverlays, map, buildingInfoWindow, findViewById(R.id.SGW), findViewById(R.id.LOY));
        mapInitializer.onCameraChange();
        mapInitializer.initializeFloorButtons(findViewById(R.id.floorButtonsGroup));
        mSearchBar = mapInitializer.initializeSearchBar(findViewById(R.id.searchBar));
        setSearchViewOnClickListener(mSearchBar, (View v) -> triggerActivityTransition());
        mapInitializer.initializeToggleButtons();
        mapInitializer.initializeLocationButton((Button) findViewById(R.id.locationButton));
        mapInitializer.initializeBuildingMarkers();
        mapInitializer.launchSettingsActivity(MapsActivity.this);

        Toast.makeText(this, "Maps is ready", Toast.LENGTH_SHORT).show();
        outdoorBuildingOverlays.overlayPolygons();
    }

    private void createLocationRequest() {
        //Building the location request
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder requestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        //Make the requests to the current settings of the device
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> tasks = settingsClient.checkLocationSettings(requestBuilder.build());

        mCameraController.goToDeviceCurrentLocation();

        tasks.addOnFailureListener(MapsActivity.this, e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvableApiException = (ResolvableApiException) e;

                try {
                    resolvableApiException.startResolutionForResult(MapsActivity.this, RESOLVABLE_API_ERROR_REQUEST_CODE);
                } catch (IntentSender.SendIntentException ex) {
                    Log.e(TAG, "Error in getting settings for location request... ");
                    LOGGER.log(Level.INFO, ex.getMessage(), ex);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 51 && resultCode == RESULT_OK) {
            mCameraController.goToDeviceCurrentLocation();
        }
    }

    private void getLocationPermission() {
        Log.d(TAG, "Getting Location Permissions");

        /** After android Marshmellow release, we need to explicitly check for
         * permissions such as location permissions*/
        String[] permissions = {
                FINE_LOCATION_PERMISSION, COURSE_LOCATION_PERMISSION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "FINE_LOCATION_PERMISSION given");

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "COURSE_LOCATION_PERMISSION given");
                mLocationPermissionsGranted = true;
            }
        } else
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void getCalendarPermission() {
        Log.d(TAG, "Getting Calendar Permissions");

        /** After android Marshmellow release, we need to explicitly check for
         * permissions such as location permissions*/
        String[] permissions = {CALENDAR_READ_PERMISSION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), CALENDAR_READ_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ_CALENDAR_PERMISSION given");
            mCalendarPermissionsGranted = true;
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, CALENDAR_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult is called");

// TODO: refactor to reduce cyclomatic complexity
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            mLocationPermissionsGranted = false;
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Location Permissions Failed " + i);
                        Log.d(TAG, " Location Permissions Failed");
                        return;
                    }
                }
                Log.d(TAG, "Location Permissions Granted");
                mLocationPermissionsGranted = true;
                initializeMap();
            }
        } else {
            if (requestCode == CALENDAR_PERMISSION_REQUEST_CODE) {
                mCalendarPermissionsGranted = false;
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "Calendar Permissions Failed " + i);
                            return;
                        }
                    }
                    Log.d(TAG, "Calendar Permissions Granted");
                    mLocationPermissionsGranted = true;
                    Log.d(TAG, "Calling calendarTryout from onRequestPermissionsResult()");
                    calendarTryout();
                }
            } else {
                Log.d(TAG, "Permissions failed due to unexpected request code: " + requestCode);
            }
        }
    }

    public void triggerActivityTransition() {
        Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MapsActivity.this, this.mSearchBar, ViewCompat.getTransitionName(this.mSearchBar));

        startActivity(intent, options.toBundle());
        finish();
    }

    public static void setSearchViewOnClickListener(View v, View.OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    public void calendarTryout() {
        Cursor calendarCursor = getCalendarCursor();
        calendarResults(calendarCursor);
    }


    public Cursor getCalendarCursor() {
        ContentResolver contentResolver = App.getContext().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selectionClause = CalendarContract.Calendars.VISIBLE + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;
        getCalendarPermission();
        @SuppressLint("MissingPermission") Cursor calendarCursor =
                contentResolver.query(uri, EVENT_PROJECTION, selectionClause, selectionArgs, sortOrder);
        return calendarCursor;
    }

    public void calendarResults(Cursor calendarCursor) {
        Log.d(TAG, "IN calendarResults()");
        // Use the cursor to step through the returned records
   /* if(calendarCursor.isNull(0)){
        Log.d(TAG, "NO CALENDARS FOUND");
    }*/
        while (calendarCursor.moveToNext()) {
            long calendarID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calendarID = calendarCursor.getLong(PROJECTION_ID_INDEX);
            displayName = calendarCursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = calendarCursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = calendarCursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            // Do something with the values...
            Log.d(TAG, "calendarID: " + calendarID + " displayName: " + displayName + " accountName: " + accountName + " ownerName: " + ownerName);

        }
    }
}