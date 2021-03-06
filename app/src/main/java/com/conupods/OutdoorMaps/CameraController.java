package com.conupods.OutdoorMaps;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CameraController {
    public static final double SGW_LAT = 45.496080;
    public static final double SGW_LNG = -73.577957;
    public static final double LOY_LAT = 45.458333;
    public static final double LOY_LNG = -73.640450;

    // LatLng objects for the campuses
    public static final LatLng SGW_CAMPUS_LOC = new LatLng(SGW_LAT, SGW_LNG);
    public static final LatLng LOY_CAMPUS_LOC = new LatLng(LOY_LAT, LOY_LNG);

    private static final float DEFAULT_ZOOM = 15f;
    private static final String TAG = "CameraController";

    private GoogleMap mMap;
    private boolean mPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;
    private LatLng mCurrentLocationCoordinates;

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
                        this.setLastKnownLocation(lastKnownLocation);
                        Log.d(TAG, "onComplete: Here is  the current lastKnownLocation: "+ lastKnownLocation);
                    } else {
                        Log.d(TAG, "onComplete: Current Location is null");
                        throw new SecurityException("Permission denied");
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceCurrentLOcation: SecurityException: " + e.getMessage());
        }
    }



    private void setCurrentLocationCoordinates(Location coordinates) {
        LatLng latLng = new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
        mCurrentLocationCoordinates = latLng;
    }



    public LatLng getCurrentLocationCoordinates() {
        return mCurrentLocationCoordinates;
    }

    public void calculateCurrentLocation() {
        if (mPermissionsGranted) {
            final Task currentLocation = mFusedLocationProvider.getLastLocation();
            currentLocation.addOnCompleteListener((@NonNull Task task) -> {
                if (task.isSuccessful()) {
                    Location lastKnownLocation = (Location) currentLocation.getResult();

                    if (lastKnownLocation != null) {
                        setCurrentLocationCoordinates(lastKnownLocation);
                    } else {
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
                                setCurrentLocationCoordinates(newLastKnownLocation);
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

    }



    public void setLastKnownLocation(Location lastKnownLocation) {
        if (lastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
            setCurrentLocationCoordinates(lastKnownLocation);
            Log.d(TAG,"setting the lastknownlocation: "+lastKnownLocation);
        } else {
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
                    setCurrentLocationCoordinates(newLastKnownLocation);
                    Log.d(TAG,"setting the newLastKnownLocation: "+newLastKnownLocation);
                }
            };

            mFusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
}
