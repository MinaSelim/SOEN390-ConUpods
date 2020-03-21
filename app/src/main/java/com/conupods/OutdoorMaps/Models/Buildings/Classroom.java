package com.conupods.OutdoorMaps.Models.Buildings;

import com.conupods.OutdoorMaps.Models.Buildings.AbstractCampusLocation;
import com.google.android.gms.maps.model.LatLng;

public class Classroom extends AbstractCampusLocation {

    public Classroom(String name, LatLng coordinates){
        super(name, coordinates);
    }



}
