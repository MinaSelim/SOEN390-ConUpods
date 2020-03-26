package com.conupods.IndoorMaps;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class HallBuildingHandler extends IndoorOverlayHandler {

    public static final LatLng CENTER_OF_HALL = new LatLng(  45.49728190486448,  	-73.57892364263535);

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
            if(bounds.contains(CENTER_OF_HALL)){
                indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.HALL);
                indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.Buildings.HALL);
            }else{
                if(nextInChain!=null) {
                    nextInChain.checkBounds(bounds, indoorBuildingOverlays);
                }
            }
    }
}
