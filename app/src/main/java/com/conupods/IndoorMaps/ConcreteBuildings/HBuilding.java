package com.conupods.IndoorMaps.ConcreteBuildings;

import com.conupods.OutdoorMaps.Building;
import com.conupods.OutdoorMaps.BuildingDataMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

public class HBuilding extends Building {

    private static BuildingDataMap mDataInstance = BuildingDataMap.getInstance();
    private static HashMap<LatLng, Building> mDataMapHash = mDataInstance.getDataMap();
    private static Building instance;

    private HBuilding(String campus, String code, String name, String longName, String address, LatLng latLng, LatLng overlayLatLng, List<String> classRooms) {
        super(campus, code, name, longName, address, latLng, overlayLatLng, classRooms);
    }

    public static Building getInstance() {
        if (instance == null) {
            instance = mDataMapHash.get(new LatLng(45.497092, -73.5788));
        }
        return instance;
    }
}