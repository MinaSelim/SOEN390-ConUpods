package com.conupods.IndoorMaps.ConcreteBuildings;


import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class VLBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static Map<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;

    public VLBuilding(String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms) {
        super(classRooms,latLng, name, null, longName, address, code, overlayLatLng);
    }

    public static Building getInstance() {
        if (instance == null) {
            instance = mDataMapHash.get(new LatLng(45.459026, -73.638606));
        }
        return instance;
    }
}