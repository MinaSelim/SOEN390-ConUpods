/**
 * @author: Patricia Nunes
 */
package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.google.android.gms.maps.model.LatLngBounds;

public class MBBuildingHandler extends IndoorOverlayHandler {

    Building mbInstance = MBBuilding.getInstance();

    /**
     * MB building handler
     * if request can't be handled, request is sent to the next in chain
     *
     * @param bounds
     * @param indoorBuildingOverlays
     */

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
