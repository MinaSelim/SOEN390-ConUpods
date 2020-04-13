package com.conupods.OutdoorMaps.Services;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


public class PlacesService {

    private final int RADIUS = 3000;

    public PlacesService() {

    }

    public String buildNearbyPlacesRequest(LatLng currentLocation, String APIKey) {
        //current location
        double mLatitude = currentLocation.latitude;
        double mLongitude = currentLocation.longitude;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=" + RADIUS);
        sb.append("&types=" + "point_of_interest");
        sb.append("&sensor=true");
        sb.append("&key="+APIKey);

        Log.d("PlacesService", "<><>api: " + sb.toString());
        return sb.toString();
    }

    public String buildPlacePhotoRequest(String photoReference, double width, String APIKey) {

        StringBuilder sb = new StringBuilder(" https://maps.googleapis.com/maps/api/place/photo?");
        sb.append("maxwidth=" + width);
        sb.append("&photoreference=" + photoReference);
        sb.append("&key="+APIKey);

        return sb.toString();
    }



}
