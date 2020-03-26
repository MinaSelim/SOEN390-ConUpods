package com.conupods.IndoorMaps;

import android.util.Log;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MBBuildingHandler extends IndoorOverlayHandler {

    Building MBInstance = MBBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if(bounds.contains(MBInstance.getLatLng())) {
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.MB);
            indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.Buildings.MB);
        }else {
            if(nextInChain!=null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
