package com.conupods.OutdoorMaps.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.conupods.MapsActivity;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.Place;
import com.conupods.OutdoorMaps.Models.PointsOfInterest.PlacesOfInterest;
import com.conupods.OutdoorMaps.Remote.IGoogleAPIService;
import com.conupods.OutdoorMaps.View.PointsOfInterest.SliderAdapter;
import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlacesService {

    private static final String TAG = "PLACES_SERVICE";
    private final int RADIUS = 2000;
    private Context mView;

    private static final double SGW_LAT = 45.496080;
    private static final double SGW_LNG = -73.577957;
    private static final double LOY_LAT = 45.458333;
    private static final double LOY_LNG = -73.640450;


    // LatLng objects for the campuses
    private static final LatLng SGW_CAMPUS_LOC = new LatLng(SGW_LAT, SGW_LNG);
    private static final LatLng LOY_CAMPUS_LOC = new LatLng(LOY_LAT, LOY_LNG);

    //Places of interest List
    List<Place> mPlacesOfInterest = new ArrayList<>();
    GoogleMap mMap;

    //Create Google API service wrapper
    IGoogleAPIService mService;

    public PlacesService(Context view, IGoogleAPIService service, GoogleMap map) {
        mView = view;
        mService = service;
        mMap = map;
    }

    public String buildNearbyPlacesRequest(LatLng currentLocation, String APIKey, String POICriteria) {
        //current location
        double mLatitude = currentLocation.latitude;
        double mLongitude = currentLocation.longitude;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=" + RADIUS);
        sb.append("&types=" + POICriteria);
        sb.append("&sensor=true");
        sb.append("&key=" + APIKey);

        Log.d("PlacesService", "<><>api: " + sb.toString());
        return sb.toString();
    }

    public String buildPlacePhotoRequest(String photoReference, int width, String APIKey) {

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?");
        sb.append("maxwidth=" + width);
        sb.append("&photoreference=" + photoReference);
        sb.append("&key=" + APIKey);

        return sb.toString();
    }

    public void getAllPointsOfInterest(String campus) {
        LatLng requestLatLng = null;
        String POICriteria = "point_of_interest";

        switch (campus) {
            case "SGW":
                requestLatLng = SGW_CAMPUS_LOC;
                POICriteria = "restaurant";

                break;
            case "LOY":
                requestLatLng = LOY_CAMPUS_LOC;
                break;
            case "Current Location":
                requestLatLng = getCurrentLocation();
                break;
            default:
                Log.d(TAG, "Incorrect campus Code");

        }

        requestPOIs(requestLatLng, POICriteria);

    }

    private void requestPOIs(LatLng requestLatLng, String POICriteria) {
        if(requestLatLng != null) {
            String placesRequestURL = buildNearbyPlacesRequest(requestLatLng, mView.getResources().getString(R.string.Google_API_Key), POICriteria);
            try {
                mService.getNearbyPlaces(placesRequestURL)
                        .enqueue(new Callback<PlacesOfInterest>() {
                            @Override
                            public void onResponse(Call<PlacesOfInterest> call, Response<PlacesOfInterest> response) {
                                if (response.isSuccessful()) {
                                    for (int i = 0; i < response.body().getResults().length; i++) {
                                        Place place = response.body().getResults()[i];

                                        if (place.getPhotos() != null) {
                                            String photoReference = place.getPhotos()[0].getPhoto_reference();
                                            int photoWidth = Integer.parseInt(place.getPhotos()[0].getWidth());
                                            String photoRequestURL = buildPlacePhotoRequest(photoReference, photoWidth, mView.getResources().getString(R.string.Google_API_Key));
                                            place.setPhotRequestURL(photoRequestURL);
                                            mPlacesOfInterest.add(place);
                                        }


                                    }

                                    SliderAdapter mSliderAdapter = new SliderAdapter(mView, mPlacesOfInterest);
                                    ViewPager viewPager = ((MapsActivity) mView).findViewById(R.id.POI_ViewPager);
                                    viewPager.setAdapter(mSliderAdapter);
                                    viewPager.setPadding(10, 0, 420, 0);


                                }
                            }

                            @Override
                            public void onFailure(Call<PlacesOfInterest> call, Throwable t) {

                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private LatLng getCurrentLocation() {
        LatLng requestLatLng;
        LocationManager locationManager = (LocationManager) mView.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mView,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mView, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) {
                requestLatLng = null;
            }
            requestLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        requestLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        return requestLatLng;
    }

    public List<Place> getPlacesOfInterest() {
        return mPlacesOfInterest;
    }


}
