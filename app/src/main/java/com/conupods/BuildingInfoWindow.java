package com.conupods;

import android.location.Address;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.List;

/**
 * An implementation of the class that deals with pop-up windows on google maps markers.
 */
public class BuildingInfoWindow implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater layoutInflater;

    public BuildingInfoWindow(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file info_window_layout
        View v = layoutInflater.inflate(R.layout.window_layout, null);

        LatLng currentLocation = marker.getPosition();
        BuildingDataMap buildingDataMap = BuildingDataMap.getInstance();
        HashMap buildingData = buildingDataMap.getDataMap();

        // Check if marker current location is a concordia building
        // TODO: will only work for accurate latlng - should maybe swap index to address or wtv
        if(buildingData.containsKey(currentLocation)){
            setTextViewContentsConcordia(v, (Building) buildingData.get(currentLocation));
        } else {
            // default behavior for non concordia buildings
        }

        // Returning the view containing InfoWindow contents
        return v;
    }

    private void setTextViewContentsConcordia(View v, Building building) {
        // Getting reference to the TextView
        TextView building_campus = (TextView) v.findViewById(R.id.building_campus);
        TextView building_longName = (TextView) v.findViewById(R.id.building_longName);
        TextView building_code = (TextView) v.findViewById(R.id.building_code);
        TextView building_address = (TextView) v.findViewById(R.id.building_address);
        TextView building_lat = (TextView) v.findViewById(R.id.building_lat);
        TextView building_lng = (TextView) v.findViewById(R.id.building_lng);

        // Setting the latitude
        building_campus.setText("Campus: " + building.getCampus());
        building_longName.setText("Name: " + building.getLongName());
        building_code.setText("Code: " + building.getCode());
        building_address.setText("Address: " + building.getAddress());
        building_lat.setText("Latitude: " + building.getLatLng().latitude);
        building_lng.setText("Longitude: " + building.getLatLng().longitude);
    }
}
