package com.conupods.IndoorMaps;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class VLBuildingHandler extends IndoorOverlayHandler {

    Building vlInstance = VLBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if(bounds.contains(vlInstance.getLatLng())){
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.CC);
        }else{
            if(nextInChain!=null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
