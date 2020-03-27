package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class MBBuildingHandler extends IndoorOverlayHandler {

    Building mbInstance = MBBuilding.getInstance();

    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if (bounds.contains(mbInstance.getLatLng())) {
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.BuildingCodes.MB);
            indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.BuildingCodes.MB);
        } else {
            if (nextInChain != null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
