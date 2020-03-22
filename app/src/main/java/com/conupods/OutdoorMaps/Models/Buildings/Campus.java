package com.conupods.OutdoorMaps.Models.Buildings;

import com.google.android.gms.maps.model.LatLng;

public class Campus extends AbstractCampusLocation {

    public Campus(String name, LatLng coordinates) {
        super(name, coordinates);
    }

    @Override
    public String getPhysicalParent() {
        return null;
    }


}
