package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

public abstract class AbstractCampusLocation {
    protected String mCodeIdentifier;
    protected String mLongIdentifier;
    protected LatLng mCoordinatesOfBuilding;
    protected AbstractCampusLocation mPhysicalParent;

    public AbstractCampusLocation() {
    }

    public AbstractCampusLocation(String codeIdentifier, LatLng cooridinates, String longIdentifier) {
        mCodeIdentifier = codeIdentifier;
        mCoordinatesOfBuilding = cooridinates;
        mLongIdentifier = longIdentifier;
    }

    public String getIdentifier() {
        return this.mCodeIdentifier;
    }

    public String getmLongIdentifier() {
        return this.mLongIdentifier;
    }

    public LatLng getCoordinates() {
        return this.mCoordinatesOfBuilding;
    }

    public abstract String getConcreteParent();


    public String toString() {
        return this.getIdentifier();
    }

}
