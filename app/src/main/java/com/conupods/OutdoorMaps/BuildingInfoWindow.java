package com.conupods.OutdoorMaps;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.conupods.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Objects;

/**
 * An implementation of the class that deals with pop-up windows on google maps markers.
 */
public class BuildingInfoWindow implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater mLayoutInflater;
    private static final String TAG = "BUILDING_INFO_WINDOW";

    public BuildingInfoWindow(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file info_window_layout
        @SuppressLint("InflateParams") View v = mLayoutInflater.inflate(R.layout.window_layout, null);

        LatLng currentLocation = marker.getPosition();
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        HashMap<LatLng, Building> buildingData = null;
        if (buildingDataMap != null) {
            buildingData = buildingDataMap.getDataMap();
        }

        if (buildingData == null) {
            Log.e(TAG, "Unable to generate building metadata.");
        } else {
            // Check if marker current location is a concordia building to add custom view contents
            if (buildingData.containsKey(currentLocation)) {
                setTextViewContentsConcordia(v, Objects.requireNonNull(buildingData.get(currentLocation)));
            } else {
                setTextViewContentsNotConcordia(v, marker);
            }
        }

        // Returning the updated view
        return v;
    }

    private void setTextViewContentsNotConcordia(View v, Marker marker) {
        TextView marketTitle = v.findViewById(R.id.title);
        marketTitle.setText(marker.getTitle());
    }

    private void setTextViewContentsConcordia(View v, Building building) {
        // Getting reference to the TextView
        TextView buildingCampus = v.findViewById(R.id.building_campus);
        TextView buildingLongName = v.findViewById(R.id.building_longName);
        TextView buildingCode = v.findViewById(R.id.title);
        TextView buildingAddress = v.findViewById(R.id.building_address);


        // Setting the text views
        buildingCampus.setText(("Campus: ").concat(building.getCampus()));
        buildingLongName.setText(("Name: ").concat(building.getLongName()));
        buildingCode.setText(("Code: ").concat(building.getCode()));
        buildingAddress.setText(("Address: ").concat(building.getAddress()));

        // TODO: Add services tab - might have to query conco API

    }

    public void generateBuildingMakers(GoogleMap mMap) {
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        HashMap<LatLng, Building> buildingData = null;
        if (buildingDataMap != null) {
            buildingData = buildingDataMap.getDataMap();
        }

        if (buildingData != null) {
            buildingData.forEach((latLng, building) -> mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(building.getLatLng().latitude, building.getLatLng().longitude))
                    .title(building.getCode())));
        } else {
            Log.e(TAG, "Unable to generate building markers due null building data.");
        }
    }
}
