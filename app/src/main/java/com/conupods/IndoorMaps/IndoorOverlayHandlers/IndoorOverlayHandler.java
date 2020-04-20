/**
 * @author: Patricia Nunes
 */
package com.conupods.IndoorMaps.IndoorOverlayHandlers;

import com.conupods.IndoorMaps.IndoorBuildingOverlays;
import com.google.android.gms.maps.model.LatLngBounds;

public abstract class IndoorOverlayHandler {
    /**
     * Chain of responsibility implementation, to deal with handling indoor overlay requests
     */

    protected IndoorOverlayHandler nextInChain;

    /**
     * sets the next handler in the chain
     *
     * @param nextInChain
     */
    public void setNextInChain(IndoorOverlayHandler nextInChain) {
        this.nextInChain = nextInChain;
    }

    /**
     * checks if the building is within view
     * displays appropriate overlay and corresponding floor buttons
     *
     * @param bounds
     * @param indoorBuildingOverlays
     */
    public abstract void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays);
}
