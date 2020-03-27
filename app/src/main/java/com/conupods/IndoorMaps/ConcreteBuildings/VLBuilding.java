package com.conupods.IndoorMaps.ConcreteBuildings;

import com.conupods.OutdoorMaps.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

public class VLBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static HashMap<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;

    private VLBuilding(String campus, String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms) {
        super(campus, code, name, longName, address, latLng, overlayLatLng, classRooms);
    }

    public static Building getInstance() {
        if (instance == null) {
            instance = mDataMapHash.get(new LatLng(45.459026, -73.638606));
        }
        return instance;
    }
}