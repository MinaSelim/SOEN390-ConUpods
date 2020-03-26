package com.conupods.IndoorMaps;

import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class CCBuildingHandler extends IndoorOverlayHandler {

    Building ccInstance = CCBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if(bounds.contains(ccInstance.getLatLng())){
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.Buildings.CC);
        }else{
            if(nextInChain!=null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
