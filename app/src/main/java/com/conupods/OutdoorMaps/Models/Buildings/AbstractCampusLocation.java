package com.conupods.OutdoorMaps.Models.Buildings;

import com.google.android.gms.maps.model.LatLng;

public class AbstractCampusLocation {
    protected String mIdentifier;
    protected LatLng mCoordinatesOfBuilding ;

    public AbstractCampusLocation(){}

    public AbstractCampusLocation(String identifier, LatLng cooridinates) {
        mIdentifier = identifier;
        mCoordinatesOfBuilding = cooridinates;
    }

    protected String getIdentifier() {
        return this.mIdentifier;
    }

    protected LatLng getCoordinates() {
        return this.mCoordinatesOfBuilding;
    }

}
