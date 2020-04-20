/**
 * @author: Patricia Nunes
 */
package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class VLBuildingHandler extends IndoorOverlayHandler {

    Building vlInstance = VLBuilding.getInstance();

    /**
     * VL building handler
     * if request cant be handled, request is sent to the next in chain
     *
     * @param bounds
     * @param indoorBuildingOverlays
     */
    @Override
    public void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays) {
        if (bounds.contains(vlInstance.getLatLng())) {
            indoorBuildingOverlays.displayOverlay(IndoorBuildingOverlays.BuildingCodes.VL);
            indoorBuildingOverlays.showFloorButtons(IndoorBuildingOverlays.BuildingCodes.VL);

        } else {
            if (nextInChain != null) {
                nextInChain.checkBounds(bounds, indoorBuildingOverlays);
            }
        }
    }
}
