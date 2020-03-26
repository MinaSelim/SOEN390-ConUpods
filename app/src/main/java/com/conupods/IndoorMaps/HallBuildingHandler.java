package com.conupods.IndoorMaps;

import android.util.Log;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class HallBuildingHandler extends IndoorOverlayHandler {

    Building HInstance = HBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
            if(bounds.contains(HInstance.getLatLng())) {
                indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.HALL);
                indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.Buildings.HALL);
            }else {
                if(nextInChain!=null) {
                    nextInChain.checkBounds(bounds, indoorBuildingOverlays);
                }
            }
    }
}
