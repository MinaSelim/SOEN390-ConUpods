package com.conupods.IndoorMaps;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public abstract class IndoorOverlayHandler {

    //next element in chain or responsibility
    protected IndoorOverlayHandler nextInChain;

    public void setNextInChain(IndoorOverlayHandler nextInChain){
        this.nextInChain = nextInChain;
    }
    public abstract void checkBounds(LatLngBounds bounds, IndoorBuildingOverlays indoorBuildingOverlays);
}
