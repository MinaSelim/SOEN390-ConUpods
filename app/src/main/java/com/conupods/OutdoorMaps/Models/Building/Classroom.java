package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

public class Classroom extends AbstractCampusLocation {
    private Building mPhysicalParent;

    public Classroom(String name, LatLng coordinates, Building parent){
        super(name, coordinates, null);
        mPhysicalParent = parent;
    }

    @Override
    public String getConcreteParent() {
        return mPhysicalParent.toString();
    }


}
