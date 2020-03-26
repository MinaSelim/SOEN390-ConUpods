package com.conupods.OutdoorMaps.Models.Building;

import com.google.android.gms.maps.model.LatLng;

public class Campus extends AbstractCampusLocation {

    public Campus(String name, LatLng coordinates) {
        super(name, coordinates);
    }

    @Override
    public String getConcreteParent() {
        return null;
    }


}
