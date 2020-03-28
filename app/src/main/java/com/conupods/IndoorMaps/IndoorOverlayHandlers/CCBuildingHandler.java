package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class CCBuildingHandler extends IndoorOverlayHandler {

    Building ccInstance = CCBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if (bounds.contains(ccInstance.getLatLng())) {
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.BuildingCodes.CC);
        } else {
            if (nextInChain != null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
