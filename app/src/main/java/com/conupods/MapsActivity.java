package com.conupods;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.BuildingInfoWindow;
import com.conupods.OutdoorMaps.CameraController;
import com.conupods.OutdoorMaps.MapInitializer;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.PlacesOfInterest;
import com.conupods.OutdoorMaps.OutdoorBuildingOverlays;
import com.conupods.OutdoorMaps.Remote.Common;
import com.conupods.OutdoorMaps.Remote.IGoogleAPIService;
import com.conupods.OutdoorMaps.Services.PlacesService;
import com.conupods.OutdoorMaps.View.PointsOfInterest.SliderAdapter;
import com.conupods.OutdoorMaps.View.SearchView.SearchActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.conupods.OutdoorMaps.View.Settings.SettingsPersonalActivity;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final Logger LOGGER = Logger.getLogger(MapsActivity.class.getName());

    private static final String TAG = "MapsActivity";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final int RESOLVABLE_API_ERROR_REQUEST_CODE = 51;

    private CameraController mCameraController;
    private SearchView mSearchBar;

    private static final String COURSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    //Variables for logic
    private boolean mLocationPermissionsGranted = false;
    private GoogleApiClient mGoogleAPIClient;
    private IGoogleAPIService mService;
    private PlacesService mPlaceService;
    private List<Place> mPlacesOfInterest = new ArrayList<>();
    private LatLng mCurrentLocation;
    private GoogleMap mMap;
    private MapInitializer mMapInitializer;

    private ViewPager mViewPager;
    private SliderAdapter mSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mService = Common.getGoogleAPIService();
        initializeMap();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        if (!mLocationPermissionsGranted) {
            getLocationPermission();

        }
        else {
            Log.d(TAG, "LINE 113 MapsActivity");
            mCameraController.goToDeviceCurrentLocation();
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
        mMap = map;
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
        MapInitializer mapInitializer = new MapInitializer(mCameraController, indoorBuildingOverlays, outdoorBuildingOverlays, map, buildingInfoWindow, findViewById(R.id.SGW), findViewById(R.id.LOY), mService,this, mSliderAdapter);
        mapInitializer.onCameraChange();
        mapInitializer.initializeFloorButtons(findViewById(R.id.floorButtonsGroup));
        mSearchBar = mapInitializer.initializeSearchBar(findViewById(R.id.searchBar));
        setSearchViewOnClickListener(mSearchBar, (View v) -> triggerActivityTransition());
        mMapInitializer.initializeToggleButtons();
        mMapInitializer.initializeLocationButton((Button) findViewById(R.id.locationButton));
        mMapInitializer.initializeBuildingMarkers();
        mMapInitializer.launchSettingsActivity(MapsActivity.this);

        Toast.makeText(this, "Maps is ready", Toast.LENGTH_SHORT).show();
        outdoorBuildingOverlays.overlayPolygons();
        initNextEventCalendarButton();
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

        Log.d(TAG, "LINE 209 MapsActivity");
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
            Log.d(TAG, "LINE 230 MapsActivity");
            mCameraController.goToDeviceCurrentLocation();
        }

    }

    private void getLocationPermission() {
        Log.d(TAG, "Getting Location Permissions");

        /** After android Marshmellow release, we need to explicitly check for
         * permissions such as location permissions
         */
        String[] permissions = {
                FINE_LOCATION_PERMISSION, COURSE_LOCATION_PERMISSION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "FINE_LOCATION_PERMISSION given");

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "COURSE_LOCATION_PERMISSION given");
                mLocationPermissionsGranted = true;
                buildGoogleAPIClient();
            }
        } else
            ActivityCompat.requestPermissions(MapsActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult is called");
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
            Log.d(TAG, "Permissions failed due to unexpected request code: " + requestCode);
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

    public synchronized void buildGoogleAPIClient() {
        mGoogleAPIClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleAPIClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) { }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleAPIClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void initNextEventCalendarButton() {
        RelativeLayout homeLayout = (RelativeLayout) findViewById(R.id.ParentMap);
        LayoutInflater inflater = getLayoutInflater();
        mMapInitializer.initNextEventButton(inflater,homeLayout);
    }


}