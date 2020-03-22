package com.conupods.OutdoorMaps.Models.Buildings;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.google.android.gms.maps.model.LatLng;

public class Classroom extends AbstractCampusLocation {
    private Building mPhysicalParent;

    public Classroom(String name, LatLng coordinates, Building parent){
        super(name, coordinates);
        mPhysicalParent = parent;
    }

    @Override
    public String getPhysicalParent() {
        return mPhysicalParent.toString();
    }


}
