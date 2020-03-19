package com.conupods.OutdoorMaps;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class CameraController {
    public final static double SGW_LAT = 45.496080;
    public final static double SGW_LNG = -73.577957;
    public final static double LOY_LAT = 45.458333;
    public final static double LOY_LNG = -73.640450;

    // LatLng objects for the campuses
    public final static LatLng SGW_CAMPUS_LOC = new LatLng(SGW_LAT, SGW_LNG);
    public final static LatLng LOY_CAMPUS_LOC = new LatLng(LOY_LAT, LOY_LNG);

    private static final float DEFAULT_ZOOM = 15f;
    private static final String TAG = "CameraController";

    private GoogleMap mMap;
    private boolean mPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;

    public CameraController(GoogleMap map, boolean permissionsGranted,
                            FusedLocationProviderClient client) {
        mMap = map;
        mPermissionsGranted = permissionsGranted;
        mFusedLocationProvider = client;
    }

    public void moveToLocationAndAddMarker(LatLng targetLocation) {
        mMap.addMarker(new MarkerOptions().position(targetLocation).title("Marker in Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(targetLocation));
    }

    public void goToDeviceCurrentLocation() {
        try {
            if (mPermissionsGranted) {
                final Task currentLocation = mFusedLocationProvider.getLastLocation();

                currentLocation.addOnCompleteListener((@NonNull Task task) -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: Got the current lastKnownLocation");
                        Location lastKnownLocation = (Location) currentLocation.getResult();

                        if (lastKnownLocation != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                        else {
                            final LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setInterval(10000);
                            locationRequest.setFastestInterval(5000);

                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    if (locationResult == null) {
                                        return;
                                    }
                                    Location newLastKnownLocation = locationResult.getLastLocation();
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                }
                            };

                            mFusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    } else {
                        Log.d(TAG, "onComplete: Current Location is null");
                        throw new RuntimeException("Permission denied");
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceCurrentLOcation: SecurityException: " + e.getMessage());
        }
    }
}
