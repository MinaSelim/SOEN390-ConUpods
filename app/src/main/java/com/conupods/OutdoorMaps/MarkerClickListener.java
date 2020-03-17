package com.conupods.OutdoorMaps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private BuildingInfoWindow mBuildingInfoWindow;

    public MarkerClickListener(GoogleMap map, BuildingInfoWindow buildingInfoWindow) {
        this.mMap = map;
        this.mBuildingInfoWindow = buildingInfoWindow;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.setInfoWindowAdapter(mBuildingInfoWindow);
        return false;
    }
}
