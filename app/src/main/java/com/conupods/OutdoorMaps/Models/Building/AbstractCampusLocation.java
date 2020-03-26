package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

public abstract class AbstractCampusLocation {
    protected String mIdentifier;
    protected LatLng mCoordinatesOfBuilding ;
    protected AbstractCampusLocation mPhysicalParent;

    public AbstractCampusLocation(){}

    public AbstractCampusLocation(String identifier, LatLng cooridinates) {
        mIdentifier = identifier;
        mCoordinatesOfBuilding = cooridinates;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }


    public LatLng getCoordinates() {
        return this.mCoordinatesOfBuilding;
    }

    public abstract String getConcreteParent();


    public  String toString() {
        return this.getIdentifier();
    }

}
