package com.conupods.OutdoorMaps.View.Directions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.IndoorMaps.View.IndoorPath;
import com.conupods.IndoorMaps.View.PathOverlay;
import com.conupods.OutdoorMaps.BuildingInfoWindow;
import com.conupods.OutdoorMaps.CameraController;
import com.conupods.OutdoorMaps.MapInitializer;
import com.conupods.OutdoorMaps.OutdoorBuildingOverlays;
import com.conupods.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;

import astar.Spot;

public class NavigationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LinearLayout layoutBottomSheet;

    private LatLng mOriginCoordinates;
    private String mOriginLongName;
    private String mOriginCode;

    private LatLng mDestinationCoordinates;
    private String mDestinationLongName;
    private String mDestinationCode;

    private TravelMode mMode;

    private RouteAdapter mAdapter;
    private List<DirectionsStep> mStepsList;

    private GeoApiContext GAC;

    private CameraController mCameraController;
    private FusedLocationProviderClient mFusedLocationProvider;
    private OutdoorBuildingOverlays mOutdoorBuildingOverlays;
    private BuildingInfoWindow mBuildingInfoWindow;
    private IndoorPath mIndoorPath;

    private IndoorBuildingOverlays mIndoorBuildingOverlays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Extract locations data
        Intent modeSelectIntent = getIntent();
        unpackIntent(modeSelectIntent);

        // Initialize the GeoAPI context
        GAC = new GeoApiContext.Builder()
                .apiKey(getString(R.string.Google_API_Key))
                .build();

        // Compute the directions, Update the view, and add the polylines
        computeDirections(mOriginCoordinates, mDestinationCoordinates, mMode);

        // Create the recycler view
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.routes_recycler_view);
        mStepsList = new ArrayList<>();
        mAdapter = new RouteAdapter(mStepsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ModeSelectActivity.class);
        loadLocationsIntoIntent(intent);
        mStepsList.clear();
        startActivity(intent);
        finish();
    }

    public void unpackIntent(Intent intent) {
        mOriginCoordinates = intent.getParcelableExtra("fromCoordinates");
        mOriginLongName = intent.getStringExtra("fromLongName");
        mOriginCode = intent.getStringExtra("fromCode");

        mDestinationCoordinates = intent.getParcelableExtra("toCoordinates");

        mDestinationLongName = intent.getStringExtra("toLongName");
        if (mDestinationLongName == null) {
            mDestinationLongName = intent.getStringExtra("toCode");
        }
        mDestinationCode = intent.getStringExtra("toCode");

        mMode = (TravelMode) intent.getSerializableExtra("mode");
    }

    private void loadLocationsIntoIntent(Intent intent) {
        intent.putExtra("fromCoordinates", mOriginCoordinates);
        intent.putExtra("fromCode", mOriginCode);
        intent.putExtra("fromLongName", mOriginLongName);

        intent.putExtra("toCoordinates", mDestinationCoordinates);
        intent.putExtra("toCode", mDestinationCode);
        intent.putExtra("toLongName", mDestinationLongName);
    }

    // Sends Directions API request
    // Calls function to update the view elements on success
    public void computeDirections(LatLng origin, LatLng destination, TravelMode mode) {

        DirectionsApiRequest directions = new DirectionsApiRequest(GAC);

        directions.origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude));
        directions.destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude));
        directions.mode(mode);

        directions.setCallback(new PendingResult.Callback<DirectionsResult>() {

            @Override
            public void onResult(DirectionsResult result) {

                if (result == null) {
                    if (result.routes.length == 0) {
                        if (result.routes[0].legs.length == 0) {
                            Toast.makeText(NavigationActivity.this, "Could not load directions", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    updateView(result);
                    addPolyLinesToMap(result);

                }
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("OUTDOORSERVICES", "Failed to get directions");
            }
        });
    }

    private void updateView(final DirectionsResult result) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // create drawer and set behavior
                for (DirectionsStep step : result.routes[0].legs[0].steps) {
                    mStepsList.add(step);
                }
                mAdapter.notifyDataSetChanged();

                ArrayList<Spot> endPoints = null;
                Log.d("NAV", mOriginCode + " " + mOriginLongName);
                Log.d("NAV", mDestinationCode + "" + mDestinationLongName);

                mIndoorPath = new IndoorPath();

                if (mOriginCode.equals(mDestinationCode)) {
                    //Class to itself
                } else {
                    if (mOriginCode.toLowerCase().equals("NA".toLowerCase()) || mOriginLongName.toLowerCase().equals("Current Location".toLowerCase())) {

                        //Current location to building
                        if (mDestinationCode.equals(mDestinationLongName)) {

                            // Single input method
                            endPoints = mIndoorPath.getIndoorPath(mDestinationCode);
                        }

                    } else {
                        if (mOriginCode.equals(mOriginLongName)) {
                            if (mDestinationCode.equals(mDestinationLongName)) {
                                //Class to class
                                endPoints = mIndoorPath.getIndoorPath(mOriginCode, mDestinationCode);
                            } else {
                                endPoints = mIndoorPath.getIndoorPath(mOriginCode);
                            }

                        } else if (mDestinationCode.equals(mDestinationLongName)) {
                            //Building outside to classroom
                            endPoints = mIndoorPath.getIndoorPath(mDestinationCode);
                        }

                    }
                    final ArrayList<Spot> points = endPoints;
                    Thread t = new Thread(() -> {
                        while (mIndoorBuildingOverlays == null) Thread.yield();
                        PathOverlay pathOverlay = new PathOverlay();
                        for (Spot point : points) {
                            pathOverlay.drawIndoorPath(mIndoorBuildingOverlays, getApplicationContext(), point);
                        }
                    });
                    t.start();
                }


            }
        });
    }

    // polyline function - can be modified to work with a single route
    private void addPolyLinesToMap(final DirectionsResult result) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (DirectionsRoute route : result.routes) {
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();
                    for (com.google.maps.model.LatLng latLng : decodedPath) {
                        newDecodedPath.add(new LatLng(latLng.lat, latLng.lng));
                    }

                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(mOriginCoordinates).title("Start of route"));
        mMap.addMarker(new MarkerOptions().position(mDestinationCoordinates).title("End of route"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOriginCoordinates, 16f));

        mOutdoorBuildingOverlays = new OutdoorBuildingOverlays(mMap, getString(R.string.geojson_url));
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        mCameraController = new CameraController(mMap, true, mFusedLocationProvider);
        mBuildingInfoWindow = new BuildingInfoWindow(getLayoutInflater());

        mIndoorBuildingOverlays = new IndoorBuildingOverlays((View)findViewById(R.id.floorButtonsGroup), mMap);
        MapInitializer mapInitializer = new MapInitializer(mCameraController, mIndoorBuildingOverlays, mOutdoorBuildingOverlays, mMap, mBuildingInfoWindow, null, null, null, null, null);
        mapInitializer.onCameraChange();
        mapInitializer.initializeFloorButtons(findViewById(R.id.floorButtonsGroup));

        mOutdoorBuildingOverlays.overlayPolygons();
    }
}
