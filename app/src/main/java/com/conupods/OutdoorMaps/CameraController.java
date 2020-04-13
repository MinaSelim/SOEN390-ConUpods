package com.conupods.OutdoorMaps;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.conupods.MapsActivity;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.PlacesOfInterest;
import com.conupods.OutdoorMaps.Remote.Common;
import com.conupods.OutdoorMaps.Remote.IGoogleAPIService;
import com.conupods.OutdoorMaps.Services.PlacesService;
import com.conupods.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private IGoogleAPIService mService;
    private PlacesService mPlaceService;
    private List<Place> mPlacesOfInterest = new ArrayList<>();
    private MapsActivity mView;


    public CameraController(GoogleMap map, boolean permissionsGranted,
                            FusedLocationProviderClient client, MapsActivity view) {
        mMap = map;
        mPermissionsGranted = permissionsGranted;
        mFusedLocationProvider = client;
        mService = Common.getGoogleAPIService();
        mPlaceService = new PlacesService();
        mView = view;

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
                        getAllPointsOfInterest();
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

    private void getAllPointsOfInterest() {

        LatLng requestLatLng = new LatLng(getCurrentLocationCoordinates().latitude,  getCurrentLocationCoordinates().longitude);
        String placesRequestURL = mPlaceService.buildNearbyPlacesRequest(requestLatLng, mView.getResources().getString(R.string.Google_API_Key));

        Log.d(TAG, "URL OF THE PLACES REQUEST: "+placesRequestURL);
        mService.getNearbyPlaces(placesRequestURL)
                .enqueue(new Callback<PlacesOfInterest>() {
                    @Override
                    public void onResponse(Call<PlacesOfInterest> call, Response<PlacesOfInterest> response) {
                        if(response.isSuccessful()) {
                            for (int i=0; i<response.body().getResults().length; i++) {
                                MarkerOptions markerOptions = new MarkerOptions();
                                Place place = response.body().getResults()[i];
                                double latitude =  Double.parseDouble(place.getGeometry().getLocation().getLat());
                                double longitude = Double.parseDouble(place.getGeometry().getLocation().getLng());

                                LatLng latLng = new LatLng(latitude, longitude);
                                String placeName = place.getName();


                                if(place.getPhotos() == null)
                                    Log.d(TAG, "PHOTO IS NULL FOR "+place);
                                else {
                                    Log.d(TAG, "PHOTO NOT  NULL FOR "+place);
                                    String photoReference = place.getPhotos()[0].getPhoto_reference();
                                    double photoWidth = Double.parseDouble(place.getPhotos()[0].getWidth());
                                    String photoRequestURL = mPlaceService.buildPlacePhotoRequest(photoReference, photoWidth, mView.getResources().getString(R.string.Google_API_Key));

                                    place.setPhotRequestURL(photoRequestURL);
                                    Log.d(TAG, "here is photo url: "+photoRequestURL);
                                    Log.d(TAG, "here is photo url from place: "+place.getPhotRequestURL());
                                }

                                markerOptions.position(latLng);
                                markerOptions.title(placeName);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                                mMap.addMarker(markerOptions);
                                mPlacesOfInterest.add(place);
                                Log.d(TAG, "CURRENT LIST OF PLACES: "+mPlacesOfInterest+"");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<PlacesOfInterest> call, Throwable t) {

                    }
                });


    }


    public List<Place> getPointsOfInterestList() {
        return mPlacesOfInterest;
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
