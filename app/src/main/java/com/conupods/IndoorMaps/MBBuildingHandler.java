package com.conupods.IndoorMaps;

import android.util.Log;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MBBuildingHandler extends IndoorOverlayHandler {


    private static final LatLng CENTER_OF_MB = new LatLng(45.49524950613837, -73.57895582914352);

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {

        if(bounds.contains(CENTER_OF_MB)){
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.MB);
            indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.Buildings.MB);
        }else{
            if(nextInChain!=null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
